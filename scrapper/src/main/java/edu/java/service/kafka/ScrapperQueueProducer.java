package edu.java.service.kafka;

import edu.java.service.sender.SenderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import request.LinkUpdateRequest;

@Slf4j
@Service
@ConditionalOnProperty(value = "app.use-queue", havingValue = "true", matchIfMissing = true)
public class ScrapperQueueProducer implements SenderService {
    private final NewTopic topic;

    private final KafkaTemplate<String, LinkUpdateRequest> template;

    public ScrapperQueueProducer(NewTopic topic, KafkaTemplate<String, LinkUpdateRequest> template) {
        this.topic = topic;
        this.template = template;
    }

    @Override
    public void updateLink(LinkUpdateRequest linkUpdateRequest) {
        try {
            template.send(topic.name(), linkUpdateRequest);
        } catch (Exception ex) {
            log.error("Error occurred during sending to Kafka", ex);
        }
    }
}
