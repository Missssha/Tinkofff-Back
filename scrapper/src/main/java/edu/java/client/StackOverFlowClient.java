package edu.java.client;

import edu.java.dto.StackOverFlowResponse;
import edu.java.models.exception.ClientException;
import edu.java.models.exception.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.RetryBackoffSpec;

public class StackOverFlowClient {

    private WebClient webClient;
    private final WebClient.Builder webClientBuilder = WebClient.builder();
    private static final String URL = "/questions/%d?site=stackoverflow";

    @Autowired
    private RetryBackoffSpec retryBackoffSpec;

    public StackOverFlowClient(String baseurl) {
        webClient = webClientBuilder.baseUrl(baseurl)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE).build();
    }

    public StackOverFlowResponse fetchQuestion(long questionId) {
        String apiUrl = String.format(URL, questionId);

        return webClient
            .get()
            .uri(apiUrl)
            .retrieve()
            .onStatus(
                HttpStatusCode::is5xxServerError,
                response -> Mono.error(new ServerException("Server error comments"))
            )
            .onStatus(
                HttpStatusCode::is4xxClientError,
                response -> Mono.error(new ClientException("Client error comments"))
            )
            .bodyToMono(StackOverFlowResponse.class)
            .retryWhen(retryBackoffSpec)
            .block();
    }
}
