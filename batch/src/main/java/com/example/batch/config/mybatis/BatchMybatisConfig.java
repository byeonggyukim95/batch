package com.example.batch.config.mybatis;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.example.batch.common.mapper.batch", sqlSessionFactoryRef = "batchSqlSessionFactory")
public class BatchMybatisConfig extends AbstractMybatisConfig {

    public BatchMybatisConfig(@Qualifier("batchDataSource") DataSource dataSource) {
        super(dataSource,
                "classpath:/sql/batch/*.xml",
                "com.example.batch.model.batch");
    }

    @Primary
    @Bean("batchSqlSessionFactory")
    @Override
    public SqlSessionFactory getSqlSessionFactory() throws Exception {
        return super.getSqlSessionFactoryBean().getObject();
    }

    @Primary
    @Bean(name = "batchTransactionManager")
    @Override
    public DataSourceTransactionManager getTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

}
