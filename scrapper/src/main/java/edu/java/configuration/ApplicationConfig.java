package edu.java.configuration;

import edu.java.configuration.retryconfig.RetryStrategy;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
@EnableRetry
public record ApplicationConfig(

    @NotNull
    @Bean
    Scheduler scheduler,

    @NotNull
    @Bean
    Retry retry,

    @NotNull
    @Bean
    BucketJ bucketj,
    @NotEmpty
    String gitUrl,
    @NotEmpty
    String stackUrl,
    @NotNull
    AccessType databaseAccessType) {
    public record Scheduler(boolean enable,
                            @NotNull Duration interval,
                            @NotNull Duration forceCheckDelay) {
    }

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
