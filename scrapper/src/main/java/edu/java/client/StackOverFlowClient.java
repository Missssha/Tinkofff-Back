package edu.java.client;

import edu.java.dto.StackOverFlowResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class StackOverFlowClient {

    private WebClient webClient;
    private final WebClient.Builder webClientBuilder = WebClient.builder();
    private static final String URL = "/questions/%d?order=%s&sort=%s&site=stackoverflow";

    public StackOverFlowClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public StackOverFlowClient(String baseurl) {
        webClient = webClientBuilder.baseUrl(baseurl)
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
