package com.cloud.tv.core.config.dbconfig;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 数据库多数据源配置
 */
//@Configuration
public class DataSourceConfig {

    // nspm数据库配置
    @Primary
    @Bean(name = "nspmDataSpringProperties")
    @ConfigurationProperties(prefix = "spring.datasource.nspm")
    public DataSourceProperties nspmDataSourceProperties(){
        return new DataSourceProperties();
    }

    // nspm数据库数据源
    @Primary
    @Bean("nspmDataSource")
    public DataSource nspmDataSource(@Qualifier("nspmDataSpringProperties") DataSourceProperties dataSourceProperties){
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    // topology数据库配置
    @Bean(name = "topologyDataSpringProperties")
    @ConfigurationProperties(prefix = "spring.datasource.topology")
    public DataSourceProperties topologyDataSourceProperties(){
        return new DataSourceProperties();
    }
    // topology数据库数据源
    @Bean("topologyDataSource")
    public DataSource topologyDataSource(@Qualifier("topologyDataSpringProperties") DataSourceProperties dataSourceProperties){
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }
}
