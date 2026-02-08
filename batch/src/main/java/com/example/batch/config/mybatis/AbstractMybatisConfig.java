package com.example.batch.config.mybatis;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

public abstract class AbstractMybatisConfig {

    protected final DataSource dataSource;
    protected final String mapperLocationsPath;
    protected final String typeAliasesPackage;

    public AbstractMybatisConfig(DataSource dataSource, String mapperLocationsPath, String typeAliasesPackage) {
        this.dataSource = dataSource;
        this.mapperLocationsPath = mapperLocationsPath;
        this.typeAliasesPackage = typeAliasesPackage;
    }

    protected final SqlSessionFactoryBean getSqlSessionFactoryBean() throws Exception {
        Configuration configuration = new Configuration();
        configuration.setCacheEnabled(Boolean.FALSE);
        configuration.setUseGeneratedKeys(Boolean.TRUE);
        configuration.setDefaultExecutorType(ExecutorType.REUSE);
        configuration.setMapUnderscoreToCamelCase(Boolean.TRUE);
        configuration.setJdbcTypeForNull(JdbcType.NULL);

        PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();

        SqlSessionFactoryBean sqlSessionFactoryBean =new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage(typeAliasesPackage);
        sqlSessionFactoryBean.setMapperLocations(patternResolver.getResources(mapperLocationsPath));
        sqlSessionFactoryBean.setConfiguration(configuration);

        return sqlSessionFactoryBean;
    }

    public abstract SqlSessionFactory getSqlSessionFactory() throws Exception;

    public abstract DataSourceTransactionManager getTransactionManager();

}
