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

    public BotQueueConsumer(
        KafkaTemplate<String, String> kafkaTemplate,
        RestApiService restApiServiceInterface
    ) {
        botQueueProducer = new BotQueueProducer(restApiServiceInterface, kafkaTemplate);
    }

    @KafkaListener(id = "myId", topics = "topic1")
    public void listen(LinkUpdateRequest linkUpdateRequest) {

        if (checkLinkUpdateRequest(linkUpdateRequest)) {
            botQueueProducer.sendNotification(linkUpdateRequest);
        } else {
            botQueueProducer.sendToDlq(linkUpdateRequest);
        }
    }

    private boolean checkLinkUpdateRequest(LinkUpdateRequest body) {
        return body.getDescription() != null && !body.getTgChatIds().isEmpty() && body.getUrl() != null
            && body.getId() != null;
    }

}
