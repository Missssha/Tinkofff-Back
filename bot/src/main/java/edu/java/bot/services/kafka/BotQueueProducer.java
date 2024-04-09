package edu.java.bot.services.kafka;

import edu.java.bot.services.RestApiService;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import request.LinkUpdateRequest;

@Log4j2
public class BotQueueProducer {
    private final RestApiService restApiServiceInterface;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public BotQueueProducer(RestApiService restApiServiceInterface, KafkaTemplate<String, String> kafkaTemplate) {
        this.restApiServiceInterface = restApiServiceInterface;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNotification(LinkUpdateRequest request) {
        restApiServiceInterface.sendNotification(request);
    }

    public void sendToDlq(LinkUpdateRequest request) {
        log.info("sending message to DLQ");
        kafkaTemplate.send("topic_dlq", request.toString());
    }
}
