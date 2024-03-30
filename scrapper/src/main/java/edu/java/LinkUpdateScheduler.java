package edu.java;

import edu.java.client.GitHubClient;
import edu.java.client.StackOverFlowClient;
import edu.java.dto.GitHubRepository;
import edu.java.dto.Link;
import edu.java.dto.StackOverFlowQuestion;
import edu.java.models.BotClient;
import edu.java.service.jdbc.JdbcLinkService;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    private final GitHubClient gitHubClient;
    private final StackOverFlowClient stackOverFlowClient;

    private final BotClient botClient = new BotClient(WebClient.builder().build());

    @Autowired
    public LinkUpdateScheduler(
        JdbcLinkService jdbcLinkService,
        GitHubClient gitHubClient,
        StackOverFlowClient stackOverFlowClient
    ) {
        this.jdbcLinkService = jdbcLinkService;
        this.gitHubClient = gitHubClient;
        this.stackOverFlowClient = stackOverFlowClient;
    }

    @Scheduled(fixedDelayString = "#{scheduler.interval}")
    public void update() {
        log.info("Update...");
        updateOldLinks();

    }

    private void updateOldLinks() {
        Timestamp now = Timestamp.valueOf(LocalDateTime.now());

        for (Link link : jdbcLinkService.getUnUpdatedLinks()) {
            try {
                URI uri = link.getUrl();
                String host = uri.getHost();
                if (host.equals("github.com")) {
                    updateGithubLink(link, now);
                } else if (host.equals("stackoverflow.com")) {
                    updateStackOverFlowLink(link, now);
                }
            } catch (URISyntaxException e) {
                log.error("Некорректный URL-адрес: {}", link.getUrl(), e);
            }
        }
    }

    private void updateGithubLink(Link link, Timestamp now) {
        String url = link.getUrl().toString();
        String owner = extractOwnerName(url);
        String repoName = extractRepoName(url);

        GitHubRepository rep = gitHubClient.getRepositoryInfo(owner, repoName).block();
        Timestamp lastPush = rep.getLastPush();

        if (lastPush.after(link.getLastCheckTime())) {
            botClient.updateLink(link.getUrl(), link.getChats());
            jdbcLinkService.updateLinkLastCheckTime(link.getId(), now);
        }
    }

    private void updateStackOverFlowLink(Link link, Timestamp now) throws URISyntaxException {
        List<String> fragments = List.of(link.getUrl().toString().split("/"));
        int idOfQuestion = Integer.parseInt(System.getenv("idQuestionName"));
        StackOverFlowQuestion
            question =
            stackOverFlowClient.fetchQuestion(Long.parseLong(fragments.get(idOfQuestion))).getItems()
                .getFirst();
        Timestamp lastActivity = question.getLastActivityAsTimestamp();

        if (lastActivity.after(link.getLastCheckTime())) {
            String description = "обновление данных : ";
            jdbcLinkService.updateLinkLastCheckTime(link.getId(), now);

            if (question.getAnswerCount() > jdbcLinkService.getLinkPropertiesById(link.getId()).getCountOfAnswer()) {
                description += "\n"
                    + "появился новый ответ";
                jdbcLinkService.updateCountOfAnswersById(link.getId(), question.getAnswerCount());
            }

            if (question.getCommentCount() > jdbcLinkService.getLinkPropertiesById(link.getId()).getCountOfComments()) {
                description += "\n"
                    + "появился новый комментарий";
                jdbcLinkService.updateCountOfCommentsById(link.getId(), question.getCommentCount());
            }

            botClient.updateLink(link.getUrl(), link.getChats());

        }
    }

    public static String extractOwnerName(String githubUrl) {
        Pattern pattern = Pattern.compile("github.com/(?<owner>[^/]+)/");
        Matcher matcher = pattern.matcher(githubUrl);

        if (matcher.find()) {
            return matcher.group("owner");
        } else {
            return null;
        }
    }

    public static String extractRepoName(String githubUrl) {
        Pattern pattern = Pattern.compile("/(?<repo>[^/]+)$");
        Matcher matcher = pattern.matcher(githubUrl);

        if (matcher.find()) {
            return matcher.group("repo");
        } else {
            return null;
        }
    }


}
