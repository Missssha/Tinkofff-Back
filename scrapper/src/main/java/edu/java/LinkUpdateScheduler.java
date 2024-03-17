package edu.java;

import edu.java.client.GitHubClient;
import edu.java.client.StackOverFlowClient;
import edu.java.dto.GitHubRepository;
import edu.java.dto.Link;
import edu.java.dto.StackOverFlowQuestion;
import edu.java.models.BotClient;
import edu.java.service.jdbc.JdbcLinkService;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Log4j2
@Component
@ConditionalOnProperty(value = "app.scheduler.enable", havingValue = "true", matchIfMissing = true)
public class LinkUpdateScheduler {
    private final JdbcLinkService jdbcLinkService;

    @Autowired
    private GitHubClient gitHubClient;

    @Autowired
    private StackOverFlowClient stackOverFlowClient;

    private final BotClient botClient = new BotClient(WebClient.builder().build());

    public LinkUpdateScheduler(JdbcLinkService jdbcLinkService) {
        this.jdbcLinkService = jdbcLinkService;
    }

    @Scheduled(fixedDelayString = "#{scheduler.interval}")
    public void update() throws InterruptedException, URISyntaxException {
        log.info("Update...");
        updateOldLinks();
    }

    private void updateOldLinks() throws URISyntaxException {
        for (Link link : jdbcLinkService.getOldLinks()) {
            if (link.getUrl().getHost().equals("github.com")) {
                updateLinkForGithub(link);
            } else if (link.getUrl().getHost().equals("stackoverflow.com")) {
                updateLinkForStackOverFlow(link);
            }
        }
    }

    private void updateLinkForGithub(Link link) throws URISyntaxException {
        int idName = Integer.parseInt(System.getenv("idName"));
        int idOfReposName = Integer.parseInt(System.getenv("idReposName"));
        List<String> fragments = List.of(link.getUrl().toString().split("/"));
        GitHubRepository rep =
            gitHubClient.getRepositoryInfo(fragments.get(idName), fragments.get(idOfReposName)).block();
        Timestamp lastPush = rep.getLastPush();
        if (lastPush.after(link.getLastCheckTime())) {
            link.setLastCheckTime(Timestamp.valueOf(LocalDateTime.now()));
            botClient.updateLink(link.getUrl(), List.of(link.getChatId()));
        }
    }

    private void updateLinkForStackOverFlow(Link link) {
        List<String> fragments = List.of(link.getUrl().toString().split("/"));
        int idOfQuestion = Integer.parseInt(System.getenv("idOfQuestion"));
        StackOverFlowQuestion
            question =
            stackOverFlowClient.fetchQuestion(Long.parseLong(fragments.get(idOfQuestion))).block().getItems()
                .getFirst();
        Timestamp lastActivity = question.getLastActivityAsTimestamp();
        if (lastActivity.after(link.getLastCheckTime())) {
            link.setLastCheckTime(Timestamp.valueOf(LocalDateTime.now()));
        }
    }
}
