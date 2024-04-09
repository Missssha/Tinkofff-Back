package edu.java.client;

import edu.java.dto.GitHubRepository;
import edu.java.models.exception.ClientException;
import edu.java.models.exception.ServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.RetryBackoffSpec;

public class GitHubClient {
    private WebClient webClient;
    private final WebClient.Builder webClientBuilder = WebClient.builder();
    @Autowired
    private RetryBackoffSpec retryBackoffSpec;

    public GitHubClient(String baseurl) {
        webClient = webClientBuilder.baseUrl(baseurl)
            .defaultHeaders(h -> h.setBearerAuth(System.getenv("GITHUB_API_TOKEN_SECOND")))
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE).build();
    }

    public GitHubRepository getRepositoryInfo(String name, String reposName) {
        return webClient
            .get()
            .uri(x -> x.path("/repos/{name}/{reposName}")
                .build(name, reposName))
            .retrieve()
            .onStatus(
                HttpStatusCode::is5xxServerError,
                response -> Mono.error(new ServerException("Server error"))
            )
            .onStatus(
                HttpStatusCode::is4xxClientError,
                response -> Mono.error(new ClientException("Client error"))
            )
            .bodyToMono(GitHubRepository.class)
            .retryWhen(retryBackoffSpec)
            .block();
    }
}
