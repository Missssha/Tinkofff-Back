package edu.java.bot.services.kafka;

import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import request.LinkUpdateRequest;

@Log4j2
public class BotQueueProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public BotQueueProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendToDlq(LinkUpdateRequest request) {
        log.info("sending message to DLQ");
        kafkaTemplate.send("topic_dlq", request.toString());
    }
}
