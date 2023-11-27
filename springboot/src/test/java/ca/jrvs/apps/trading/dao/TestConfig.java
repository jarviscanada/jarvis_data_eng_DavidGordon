package ca.jrvs.apps.trading.dao;

import ca.jrvs.apps.trading.AppConfig;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
public class TestConfig implements EnvironmentAware {
    private Environment env;
    @Override
    public void setEnvironment(Environment environment) {
        this.env = environment;
    }

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(env.getProperty("jdbc:postgresql://localhost:5432/jrvstrading"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
        return dataSource;
    }
}
