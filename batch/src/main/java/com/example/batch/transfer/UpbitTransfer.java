package com.example.batch.transfer;

import com.example.batch.model.properties.UpbitProperties;
import com.example.batch.model.upbit.resp.CoinCandlesResp;
import com.example.batch.model.upbit.req.CoinCandlesReq;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;

@Slf4j
@Service
public class UpbitTransfer {

    private final WebClient webClient;
    private final UpbitProperties upbitProperties;

    public UpbitTransfer(UpbitProperties upbitProperties) {
        this.webClient = WebClient.create(upbitProperties.url());
        this.upbitProperties = upbitProperties;
    }

    public List<CoinCandlesResp> getCoinCandles(CoinCandlesReq coinCandlesReq) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(upbitProperties.candlesPath())
                        .queryParam("market", coinCandlesReq.getMarket())
                        .queryParam("count", coinCandlesReq.getCount())
                        .build(coinCandlesReq.getUnit()))
                .retrieve()
                .bodyToFlux(CoinCandlesResp.class)
                .collectList()
                .timeout(Duration.ofSeconds(5))
                .doOnError(error -> log.error("Upbit API 호출 실패", error))
                .block();
    }

}
