package edu.java.models;

import edu.java.dto.Chat;
import edu.java.models.Request.LinkUpdateRequest;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.apache.kafka.common.errors.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class BotClient {
    private final String baseUrl = "http://localhost:8090";

    private final WebClient webClient;

    public BotClient(WebClient webClient) {
        this.webClient = webClient;
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
            .onStatus(HttpStatus.BAD_REQUEST::equals, response -> Mono.error(new ApiException()))
            .bodyToMono(String.class)
            .block();
    }

}
