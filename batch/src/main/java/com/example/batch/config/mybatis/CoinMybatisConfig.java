package com.example.batch.config.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.example.batch.common.mapper.coin", sqlSessionFactoryRef = "coinSqlSessionFactory")
public class CoinMybatisConfig extends AbstractMybatisConfig {

    public CoinMybatisConfig(@Qualifier("coinDataSource") DataSource dataSource) {
        super(dataSource,
                "classpath:/sql/coin/*.xml",
                "com.example.batch.model.coin");
    }

    @Bean("coinSqlSessionFactory")
    @Override
    public SqlSessionFactory getSqlSessionFactory() throws Exception {
        return super.getSqlSessionFactoryBean().getObject();
    }

    @Bean(name = "coinTransactionManager")
    @Override
    public DataSourceTransactionManager getTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

}
