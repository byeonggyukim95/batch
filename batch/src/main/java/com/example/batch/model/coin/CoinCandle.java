package com.example.batch.model.coin;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class CoinCandle {

    private String market;
    private String candleDateTimeKst;
    private BigDecimal openingPrice;
    private BigDecimal tradePrice;
    private Integer unit;

}