package edu.java.bot.services.kafka;

import edu.java.bot.services.RestApiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import request.LinkUpdateRequest;

@Service
@Slf4j
public class BotQueueConsumer {
    private final BotQueueProducer botQueueProducer;
    private final RestApiService restApiServiceInterface;

    public BotQueueConsumer(
        KafkaTemplate<String, String> kafkaTemplate,
        RestApiService restApiServiceInterface
    ) {
        this.restApiServiceInterface = restApiServiceInterface;
        botQueueProducer = new BotQueueProducer(kafkaTemplate);
    }

    @KafkaListener(id = "myId", topics = "topic1")
    public void listen(LinkUpdateRequest linkUpdateRequest) {
        try {
            if (checkLinkUpdateRequest(linkUpdateRequest)) {
                restApiServiceInterface.sendNotification(linkUpdateRequest);
            } else {
                botQueueProducer.sendToDlq(linkUpdateRequest);
            }
        } catch (Exception e) {
            log.info("Error sending message");
            botQueueProducer.sendToDlq(linkUpdateRequest);
        }

    }

    private boolean checkLinkUpdateRequest(LinkUpdateRequest body) {
        return body.getDescription() != null && !body.getTgChatIds().isEmpty() && body.getUrl() != null
            && body.getId() != null;
    }

}
