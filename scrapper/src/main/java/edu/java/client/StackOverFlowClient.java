package edu.java.client;

import edu.java.dto.StackOverFlowResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


public class StackOverFlowClient {

    private WebClient webClient;
    private static final String URL = "/questions/%d?order=%s&sort=%s&site=stackoverflow";
    private static final String STACKOVERFLOW = "https://api.stackexchange.com/2.3";

    public StackOverFlowClient(WebClient webClient) {
    }

    @Bean
    public WebClient stackOverFlowClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl(STACKOVERFLOW)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE).build();
    }

    public Mono<StackOverFlowResponse> fetchQuestion(long questionId, String sort, String order) {
        String apiUrl = String.format(URL, questionId, sort, order);

        return webClient
            .get()
            .uri(apiUrl)
            .retrieve()
            .bodyToMono(StackOverFlowResponse.class);
    }
}

