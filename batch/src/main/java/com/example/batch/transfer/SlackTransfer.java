package com.example.batch.transfer;

import com.example.batch.model.SlackMessage;
import com.example.batch.model.properties.SlackProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
public class SlackTransfer {

    private final WebClient webClient;

    public SlackTransfer(SlackProperties slackProperties) {
        this.webClient = WebClient.create(slackProperties.webhook());
    }

    public void sendSlackWebhook(String message) {
        try {
            webClient.post()
                    .bodyValue(new SlackMessage(message))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

        } catch (Exception e) {
            log.error("Slack Webhook 전송 실패", e);
        }
    }

}
