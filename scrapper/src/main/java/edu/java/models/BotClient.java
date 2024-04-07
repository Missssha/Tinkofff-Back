package edu.java.models;

import edu.java.models.exception.ClientException;
import edu.java.models.exception.ServerException;
import edu.java.service.sender.SenderService;
import org.apache.kafka.common.errors.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.RetryBackoffSpec;
import request.LinkUpdateRequest;

public class BotClient implements SenderService {
    private final String baseUrl = "http://localhost:8090";
    private final WebClient webClient = WebClient.builder().build();

    private final RetryBackoffSpec retryBackoffSpec;

    public BotClient(RetryBackoffSpec retryBackoffSpec) {
        this.retryBackoffSpec = retryBackoffSpec;
    }

    @Override
    public void updateLink(LinkUpdateRequest linkUpdateRequest) {
        webClient
            .post()
            .uri(baseUrl + "/updates")
            .body(Mono.just(linkUpdateRequest), LinkUpdateRequest.class)
            .header("Accept", "application/json")
            .retrieve()
            .onStatus(
                HttpStatusCode::is5xxServerError,
                response -> Mono.error(new ServerException("Server error"))
            )
            .onStatus(
                HttpStatusCode::is4xxClientError,
                response -> Mono.error(new ClientException("Client error"))
            )
            .onStatus(HttpStatus.BAD_REQUEST::equals, response -> Mono.error(new ApiException()))
            .bodyToMono(String.class)
            .retryWhen(retryBackoffSpec)
            .block();
    }

}
