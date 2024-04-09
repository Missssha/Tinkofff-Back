package edu.java.bot.configurations.retryconfig;

import java.time.Duration;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;
import reactor.util.retry.RetryBackoffSpec;

@Configuration
@ConditionalOnProperty(prefix = "app.retry", name = "strategy", havingValue = "const")
public class ConstantStrategy {
    @Value("${app.retry.max-attempts}")
    private int maxAttempts;
    @Value("${app.retry.delay}")
    private int delay;
    @Value("${app.retry.statuses}")
    List<Integer> statuses;

    @Bean
    public RetryBackoffSpec retryBackoffSpec() {
        return Retry
            .fixedDelay(maxAttempts, Duration.ofSeconds(delay))
            .filter(throwable -> isContains(throwable, statuses));
    }

    private boolean isContains(Throwable throwable, List<Integer> statusCodes) {
        if (throwable instanceof WebClientResponseException wcre) {
            return statusCodes.contains(wcre.getStatusCode().value());
        }
        return false;
    }
}
