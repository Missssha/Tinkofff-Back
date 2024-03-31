package edu.java.configuration;

import edu.java.configuration.retryconfig.RetryStrategy;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
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
    @NotEmpty
    String gitUrl,
    @NotEmpty
    String stackUrl,
    @NotNull
    String retryOn,
    @NotNull
    RetryStrategy retryStrategy,
    @NotNull
    int retryMaxAttempts,
    @NotNull
    int retryDelay,
    @NotNull
    int capacity,
    @NotNull
    int refill,
    @NotNull
    int timeout) {
    public record Scheduler(boolean enable,
                            @NotNull Duration interval,
                            @NotNull Duration forceCheckDelay) {
    }

}
