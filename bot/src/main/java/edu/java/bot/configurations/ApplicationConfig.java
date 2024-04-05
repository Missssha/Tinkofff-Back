package edu.java.bot.configurations;

import edu.java.bot.configurations.retryconfig.RetryStrategy;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
public record ApplicationConfig(
    @NotNull
    @Bean
    Retry retry,

    @NotNull
    @Bean
    BucketJ bucket,
    @NotEmpty
    String telegramToken
) {
    public record Retry(@NotNull String trigger,
                        @NotNull RetryStrategy strategy,
                        @NotNull int maxAttempts,
                        @NotNull int delay,
                        @NotNull List<Integer> statuses) {
    }

    public record BucketJ(@NotNull int refill,
                          @NotNull int capacity,
                          @NotNull int timeout) {
    }
}
