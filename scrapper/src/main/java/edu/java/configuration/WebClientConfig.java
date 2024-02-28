package edu.java.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    private static final String GITHUB = "https://api.github.com/";
    private static final String STACKOVERFLOW = "https://api.stackexchange.com/2.3";
    @Bean
    public WebClient gitHubClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
            .baseUrl(GITHUB)
            .defaultHeaders(h -> h.setBearerAuth(System.getenv("GITHUB_API_TOKEN_SECOND")))
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }

    @Bean
    public WebClient stackOverFlowClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
            .baseUrl(STACKOVERFLOW)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .build();
    }
}
