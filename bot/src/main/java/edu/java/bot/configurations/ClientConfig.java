package edu.java.bot.configurations;

import edu.java.bot.models.ScrapperClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.util.retry.RetryBackoffSpec;

@Configuration
public class ClientConfig {

    @Autowired
    private RetryBackoffSpec retryBackoffSpec;

    @Bean
    public ScrapperClient botClient() {
        return new ScrapperClient(retryBackoffSpec);
    }
}
