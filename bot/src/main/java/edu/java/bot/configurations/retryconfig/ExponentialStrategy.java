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
@ConditionalOnProperty(prefix = "app,retry", name = "strategy", havingValue = "exponential")
public class ExponentialStrategy {
    @Value("${app.retry.max-attempts}")
    private int maxAttempts;

    @Value("${app.retry.delay}")
    private int delay;

    @Value("${app.retry.statuses}")
    List<Integer> statuses;

    @Bean
    public RetryBackoffSpec retryBackoffSpec() {
        return Retry
            .backoff(maxAttempts, Duration.ofSeconds(delay))
            .filter(throwable -> getStatusCodes(throwable, statuses));
    }

    private boolean getStatusCodes(Throwable throwable, List<Integer> statusCodes) {
        if (throwable instanceof WebClientResponseException) {
            int statusCode = ((WebClientResponseException) throwable).getStatusCode().value();
            return statusCodes.contains(statusCode);
        }
        return false;
    }
}
