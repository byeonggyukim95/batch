package com.example.batch.model.upbit.req;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CoinCandlesReq {

    private String market;
    private int count;
    private int unit;

}
