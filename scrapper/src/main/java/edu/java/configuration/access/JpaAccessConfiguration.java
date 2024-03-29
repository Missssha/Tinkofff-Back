package edu.java.configuration.access;

import edu.java.repository.jpa.JpaChatRepository;
import edu.java.repository.jpa.JpaLinkRepository;
import edu.java.service.ChatService;
import edu.java.service.LinkService;
import edu.java.service.jpa.JpaChatService;
import edu.java.service.jpa.JpaLinkService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfiguration {
    @Bean
    public LinkService linkService(
        JpaLinkRepository linkRepository,
        JpaChatRepository chatRepository
    ) {
        return new JpaLinkService(linkRepository, chatRepository);
    }

    @Bean
    public ChatService chatService(
        JpaChatRepository chatRepository,
        JpaLinkRepository linkRepository
    ) {
        return new JpaChatService(chatRepository, linkRepository);
    }
}
