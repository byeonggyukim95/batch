package com.example.batch.model.properties;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "app.slack")
@Validated
public record SlackProperties(
        @NotBlank String webhook
) {
}
