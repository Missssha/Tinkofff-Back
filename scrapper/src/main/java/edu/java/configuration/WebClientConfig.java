package edu.java.configuration;

import edu.java.client.GitHubClient;
import edu.java.client.StackOverFlowClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebClientConfig {
    private static final String GITHUB = "https://api.github.com/";
    private static final String STACKOVERFLOW = "https://api.stackexchange.com/2.3";


    @Bean
    public GitHubClient gitHubClient() {
        return new GitHubClient(GITHUB);
    }


    @Bean
    public StackOverFlowClient stackOverFlowClient() {
        return new StackOverFlowClient(STACKOVERFLOW);
    }
}
