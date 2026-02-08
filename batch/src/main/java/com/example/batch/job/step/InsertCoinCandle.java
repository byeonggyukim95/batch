package com.example.batch.job.step;

import com.example.batch.common.mapper.coin.CoinMapper;
import com.example.batch.model.upbit.resp.CoinCandlesResp;
import com.example.batch.model.upbit.req.CoinCandlesReq;
import com.example.batch.transfer.UpbitTransfer;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.support.IteratorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.util.List;

@Configuration
public class InsertCoinCandle {

    private final int CHUNK_SIZE = 1;

    private final JobRepository jobRepository;
    private final PlatformTransactionManager coinTransactionManager;
    private final CoinMapper coinMapper;
    private final UpbitTransfer upbitTransfer;

    @Autowired
    public InsertCoinCandle(
            JobRepository jobRepository,
            @Qualifier("coinTransactionManager") PlatformTransactionManager coinTransactionManager,
            CoinMapper coinMapper,
            UpbitTransfer upbitTransfer) {
        this.jobRepository = jobRepository;
        this.coinTransactionManager = coinTransactionManager;
        this.coinMapper = coinMapper;
        this.upbitTransfer = upbitTransfer;
    }

    @Bean("insertCoinCandleStep")
    public Step InsertCoinCandleStep(
            @Qualifier("coinCandlesReader") ItemReader<CoinCandlesResp> reader,
            @Qualifier("coinCandlesWriter") ItemWriter<CoinCandlesResp> writer) throws Exception {
        return new StepBuilder("insertCoinCandleStep", jobRepository)
                .<CoinCandlesResp, CoinCandlesResp>chunk(CHUNK_SIZE)
                .transactionManager(coinTransactionManager)
                .reader(reader)
                .writer(writer)
                .build();
    }

    @Bean
    @StepScope
    public IteratorItemReader<CoinCandlesResp> coinCandlesReader() {
        List<CoinCandlesResp> coinCandlesList = upbitTransfer.getCoinCandles(
                CoinCandlesReq.builder()
                        .market("KRW-BTC")
                        .count(1)
                        .unit(60)
                        .build());
        return new IteratorItemReader<>(coinCandlesList.iterator());
    }

    @Bean
    public ItemWriter<CoinCandlesResp> coinCandlesWriter() {
        return items -> {
            for (CoinCandlesResp coinCandles : items) {
                coinMapper.insertCoinCandle(coinCandles);
            }
        };
    }

}