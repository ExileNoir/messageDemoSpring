package com.example.demoguarani;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author sDeseure
 * @project demoguarani
 * @date 22/09/2021
 */

@Configuration
public class DemoGuaraniConfig {

    @Bean(name = "pg13")
    @ConfigurationProperties(prefix = "datasource")
    @Primary
    public DataSource createDataSource() {
        return DataSourceBuilder
                .create()
                .build();
    }

    @Bean
    public JdbcTemplate createJdbcTemplate(@Qualifier("pg13") final DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public DSLContext createDslContext(final DataSource dataSource) {
        return DSL.using(dataSource, SQLDialect.POSTGRES);
    }
}
