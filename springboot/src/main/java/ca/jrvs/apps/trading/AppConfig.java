package ca.jrvs.apps.trading;

import ca.jrvs.apps.trading.dao.MarketDataHttpHelper;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class AppConfig implements EnvironmentAware {

    @Bean
    public MarketDataHttpHelper marketDataHttpHelper() {
        return new MarketDataHttpHelper();
    }

    private static Environment env;


    public static String getProperty(String key) {
        return env.getProperty(key);
    }

    @Override
    public void setEnvironment(final Environment environment) {
        env = environment;
    }
}
