package com.bl.lms.configuration;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(System.getenv().get("datasource.driver-class-name"));
        dataSourceBuilder.url(System.getenv().get("database.url"));
        dataSourceBuilder.username(System.getenv().get("database.username"));
        dataSourceBuilder.password(System.getenv().get("database.password"));
        return dataSourceBuilder.build();
    }
}
