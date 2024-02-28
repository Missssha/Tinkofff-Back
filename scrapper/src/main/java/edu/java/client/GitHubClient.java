package edu.java.client;

import edu.java.dto.GitHubRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class GitHubClient {
    private final WebClient webClient;

    public GitHubClient(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping("/repos/{name}/{reposName}")
    public Mono<GitHubRepository> getRepositoryInfo(
        @PathVariable("name") String name,
        @PathVariable("reposName") String reposName
    ) {
        return webClient
            .get()
            .uri(x -> x.path("/repos/{name}/{reposName}")
                .build(name, reposName))
            .retrieve()
            .bodyToMono(GitHubRepository.class);
    }
}
