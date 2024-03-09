package edu.java.bot.configurations;

import edu.java.bot.processors.GitHubUrlProcessor;
import edu.java.bot.processors.StackOverFlowUrlProcessor;
import edu.java.bot.processors.UrlProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class URLProcessorConfig {

    @Bean
    UrlProcessor urlProcessor() {
        return new StackOverFlowUrlProcessor(
            new GitHubUrlProcessor(null)
        );
    }
}
