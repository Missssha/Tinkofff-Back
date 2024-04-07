package edu.java.configuration;

import edu.java.client.GitHubClient;
import edu.java.client.StackOverFlowClient;
import edu.java.models.BotClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.util.retry.RetryBackoffSpec;

@Configuration
public class ClientConfig {
    @Value("${app.gitUrl}")
    private String gitUrl;

    @Value("${app.stackUrl}")
    private String stackUrl;

    @Bean
    public GitHubClient gitHubClient() {
        return new GitHubClient(gitUrl);
    }

    @Bean
    public StackOverFlowClient stackOverFlowClient() {
        return new StackOverFlowClient(stackUrl);
    }

    @Autowired
    private RetryBackoffSpec retryBackoffSpec;

    @Bean
    @ConditionalOnProperty(value = "app.use-queue", havingValue = "false", matchIfMissing = true)
    public BotClient botClient() {
        return new BotClient(retryBackoffSpec);
    }
}
