package edu.java.configuration;

import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class SpringJdbcConfig {

    private String postgres = "postgres";

    @Bean
    public DataSource myPostgresDataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl("jdbc:postgresql://localhost:5432/scrapper");
        ds.setUsername(postgres);
        ds.setPassword(postgres);

        return ds;
    }
}
