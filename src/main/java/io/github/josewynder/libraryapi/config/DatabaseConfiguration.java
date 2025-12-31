package io.github.josewynder.libraryapi.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driver;

//    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driver);
        return dataSource;
    }


    /**
     * Hikari configuration
     * https://github.com/brettwooldridge/HikariCP
     * @return
     */
//    @Bean
    public DataSource hikariDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);

        config.setMaximumPoolSize(10); // Maximum number of connections allowed
        config.setMinimumIdle(1); // Initial pool size
        config.setPoolName("library-db-pool");
        config.setMaxLifetime(600_000); // Six hundred thousand millisecond (ten minutes)
        config.setConnectionTimeout(100_000); // Timeout to establish a connection
        config.setConnectionTestQuery("SELECT 1"); // Query test

        return new HikariDataSource(config);
    }
}
