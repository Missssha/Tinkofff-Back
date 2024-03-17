package edu.java.configuration;

import edu.java.client.GitHubClient;
import edu.java.client.StackOverFlowClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebClientConfig {
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
}
