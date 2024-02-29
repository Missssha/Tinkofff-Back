package edu.java.client;

import edu.java.dto.GitHubRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class GitHubClient {
    private WebClient webClient;
    private static final String GITHUB = "https://api.github.com/";

    public GitHubClient(WebClient webClient) {
    }

    @Bean
    public WebClient gitHubClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder.baseUrl(GITHUB)
            .defaultHeaders(h -> h.setBearerAuth(System.getenv("GITHUB_API_TOKEN_SECOND")))
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE).build();
    }

    public Mono<GitHubRepository> getRepositoryInfo(String name, String reposName) {
        return webClient
            .get()
            .uri(x -> x.path("/repos/{name}/{reposName}")
                .build(name, reposName))
            .retrieve()
            .bodyToMono(GitHubRepository.class);
    }
}
