package edu.java.controllers;

import edu.java.clients.GitHubClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class GitHubController {
    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping("repos/{name}/{nameOfRepository}")
    public GitHubClient getGitHubClient(@PathVariable("name") String name,
        @PathVariable("nameOfRepository") String nameOfRepository){
        return webClientBuilder
            .build()
            .get()
            .uri("https://api.github.com/repos/{name}/{nameOfRepository}", name, nameOfRepository)
            .retrieve()
            .bodyToMono(GitHubClient.class)
            .block();
    }

}
