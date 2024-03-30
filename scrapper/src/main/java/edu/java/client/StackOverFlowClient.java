package edu.java.client;

import edu.java.dto.StackOverFlowQuestion;
import edu.java.dto.StackOverFlowResponse;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

public class StackOverFlowClient {

    private WebClient webClient;
    private final WebClient.Builder webClientBuilder = WebClient.builder();
    private static final String URL = "/questions/%d?site=stackoverflow";
    private static final String URLSOF = "/questions/%d/comments?site=stackoverflow";

    public StackOverFlowClient(String baseurl) {
        webClient = webClientBuilder.baseUrl(baseurl)
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE).build();
    }

    public StackOverFlowResponse fetchQuestion(long questionId) {
        String apiUrl = String.format(URL, questionId);
        String commentsUrl = String.format(URLSOF, questionId);

        Long comments = (long) webClient
            .get()
            .uri(commentsUrl)
            .retrieve()
            .bodyToMono(StackOverFlowResponse.class).block().getItems().size();

        StackOverFlowResponse response = webClient
            .get()
            .uri(apiUrl)
            .retrieve()
            .bodyToMono(StackOverFlowResponse.class).block();

        StackOverFlowResponse list = new StackOverFlowResponse();
        StackOverFlowQuestion question = response.getItems().getFirst();
        question.setCommentCount(comments);
        list.setItems(List.of(question));

        return list;
    }
}
