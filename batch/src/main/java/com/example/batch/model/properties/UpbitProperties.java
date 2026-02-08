package com.example.batch.model.properties;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "app.upbit")
@Validated
public record UpbitProperties(
        @NotBlank String url,
        @NotBlank String candlesPath
) {
}
