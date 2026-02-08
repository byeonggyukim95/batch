package com.example.batch.job.step;

import com.example.batch.common.mapper.coin.CoinMapper;
import com.example.batch.model.coin.CoinCandle;
import com.example.batch.transfer.SlackTransfer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Objects;

@Configuration
public class SendCoinCandle {

    private static final String KTW_BTC = "KRW-BTC";

    private final JobRepository jobRepository;
    private final CoinMapper coinMapper;
    private final PlatformTransactionManager coinTransactionManager;
    private final SlackTransfer slackTransfer;

    public SendCoinCandle(
            JobRepository jobRepository,
            CoinMapper coinMapper,
            @Qualifier("coinTransactionManager") PlatformTransactionManager coinTransactionManager,
            SlackTransfer slackTransfer) {
        this.jobRepository = jobRepository;
        this.coinMapper = coinMapper;
        this.coinTransactionManager = coinTransactionManager;
        this.slackTransfer = slackTransfer;
    }

    @Bean("sendCoinCandleStep")
    public Step SendCoinCandleStep(@Qualifier("sendCoinCandleTasklet") Tasklet tasklet) {
        return new StepBuilder("sendCoinCandleStep", jobRepository)
                .tasklet(tasklet, coinTransactionManager)
                .build();
    }

    @Bean
    public Tasklet sendCoinCandleTasklet() {
        return (contribution, chunkContext) -> {
            CoinCandle coinCandle = coinMapper.selectCoinCandleByMarket(KTW_BTC);
            if (Objects.isNull(coinCandle)) {
                return RepeatStatus.FINISHED;
            }

            BigDecimal rate = BigDecimal.ZERO;
            if (coinCandle.getOpeningPrice().compareTo(BigDecimal.ZERO) > 0) {
                rate = coinCandle.getTradePrice()
                        .subtract(coinCandle.getOpeningPrice())
                        .divide(coinCandle.getOpeningPrice(), 4, RoundingMode.DOWN)
                        .multiply(BigDecimal.valueOf(100))
                        .setScale(2, RoundingMode.DOWN);
            }

            DecimalFormat decimalFormat = new DecimalFormat("###,###.##");

            String priceMessage = """
                    [%s]
                    종가: %s
                    시가: %s
                    변동률: %s%% (최근 1시간)
                    """.formatted(
                    coinCandle.getMarket(),
                    decimalFormat.format(coinCandle.getTradePrice().setScale(2, RoundingMode.DOWN)),
                    decimalFormat.format(coinCandle.getOpeningPrice().setScale(2, RoundingMode.DOWN)),
                    decimalFormat.format(rate)
            );

            slackTransfer.sendSlackWebhook(priceMessage);

            return RepeatStatus.FINISHED;
        };
    }

}
