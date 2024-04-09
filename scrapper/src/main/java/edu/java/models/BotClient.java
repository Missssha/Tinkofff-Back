package edu.java.models;

import edu.java.dto.Chat;
import edu.java.models.Request.LinkUpdateRequest;
import edu.java.models.exception.ClientException;
import edu.java.models.exception.ServerException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.apache.kafka.common.errors.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.RetryBackoffSpec;

public class BotClient {
    private final String baseUrl = "http://localhost:8090";
    private final WebClient webClient = WebClient.builder().build();

    private final RetryBackoffSpec retryBackoffSpec;

    public BotClient(RetryBackoffSpec retryBackoffSpec) {
        this.retryBackoffSpec = retryBackoffSpec;
    }

    public String updateLink(URI url, List<Chat> tgChatIds) {
        List<Long> transformedTgChatIds = new ArrayList<>();
        for (Chat chat : tgChatIds) {
            transformedTgChatIds.add(chat.getId());
        }
        LinkUpdateRequest linkUpdateRequest = new LinkUpdateRequest(1L, url, "Обновление ссылки", transformedTgChatIds);
        return webClient
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
