package com.example.batch.model.upbit.resp;

import lombok.Getter;
import lombok.Setter;
import tools.jackson.databind.PropertyNamingStrategies;
import tools.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;

@Setter
@Getter
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CoinCandlesResp {

    private String market;
    private String candleDateTimeUtc;
    private String candleDateTimeKst;
    private BigDecimal openingPrice;
    private BigDecimal highPrice;
    private BigDecimal lowPrice;
    private BigDecimal tradePrice;
    private Long timestamp;
    private BigDecimal candleAccTradePrice;
    private BigDecimal candleAccTradeVolume;
    private Integer unit;

}
