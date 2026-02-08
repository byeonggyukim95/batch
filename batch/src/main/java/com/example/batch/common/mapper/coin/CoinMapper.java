package com.example.batch.common.mapper.coin;

import com.example.batch.model.coin.CoinCandle;
import com.example.batch.model.upbit.resp.CoinCandlesResp;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

@Mapper
public interface CoinMapper {

    CoinCandle selectCoinCandleByMarket(@Param("market") String market) throws DataAccessException;

    void insertCoinCandle(CoinCandlesResp coinCandlesResp) throws DataAccessException;

}
