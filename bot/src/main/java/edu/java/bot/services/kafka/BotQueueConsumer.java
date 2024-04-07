package edu.java.bot.services.kafka;

import edu.java.bot.services.RestApiServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import request.LinkUpdateRequest;

@Service
@Slf4j
public class BotQueueConsumer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    private final RestApiServiceInterface restApiServiceInterface;

    public BotQueueConsumer(
        KafkaTemplate<String, String> kafkaTemplate,
        RestApiServiceInterface restApiServiceInterface
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.restApiServiceInterface = restApiServiceInterface;
    }

    @KafkaListener(id = "myId", topics = "topic1")
    public void listen(LinkUpdateRequest linkUpdateRequest) {

        if (checkLinkUpdateRequest(linkUpdateRequest)) {
            restApiServiceInterface.sendNotification(linkUpdateRequest);
        } else {
            log.info("sending message to DLQ");
            kafkaTemplate.send("topic_dlq", linkUpdateRequest.toString());
        }

    }

    private boolean checkLinkUpdateRequest(LinkUpdateRequest body) {
        return body.getDescription() != null && !body.getTgChatIds().isEmpty() && body.getUrl() != null
            && body.getId() != null;
    }

}
