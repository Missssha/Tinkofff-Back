package edu.java.models;

import edu.java.models.Request.LinkUpdateRequest;
import java.net.URI;
import java.net.URISyntaxException;
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

    public String updateLink(String url, List<Long> tgChatIds) throws URISyntaxException {
        LinkUpdateRequest linkUpdateRequest = new LinkUpdateRequest(1L, new URI(url), "Обновление ссылки", tgChatIds);

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
