package com.aslanjavasky.shawarmadelviry.data.repoImpls.statement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.postgresql.Driver;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

//@Configuration
public class DatabaseConfig {

//    @Value("${spring.datasource.url}")
//    public String url;
//
//    @Value("${spring.datasource.username}")
//    public String username;
//
//   @Value("${spring.datasource.password}")
//   public String password;
//
//   @Value("${spring.datasource.driver-class-name}")
//   public String driverClassName;
////
//    @Bean
//    public DataSource dataSource(){
//        return DataSourceBuilder.create()
//                .url(url)
//                .username(username)
//                .password(password)
//                .driverClassName(driverClassName)
//                .type(DriverManagerDataSource.class)
//                .build();
//    }
//
//    @Bean
//    public NamedParameterJdbcTemplate namedParamJdbcTemplate(DataSource dataSource){
//        return new NamedParameterJdbcTemplate (dataSource);
//    }
}
