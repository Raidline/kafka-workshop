package com.ctw.summit.promo.kafka;

import com.ctw.summit.promo.service.PromoService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Properties;

@Component
@RequiredArgsConstructor
@Slf4j
public class PromoConsumer {

    private static final String PROMO_TOPIC = "promo.error";

    private final Properties kafkaConsumerProperties;
    private final PromoService service;

    @PostConstruct
    public void startConsumer() {
        new Thread(this::poolRecord, "Promo-Error-Poller").start();
    }

    private void poolRecord() {
        var consumer = new KafkaConsumer<>(kafkaConsumerProperties, new StringDeserializer(),
                new PromoDeserializer());

        consumer.subscribe(List.of(PROMO_TOPIC));

        while (true) {

            var records = consumer.poll(Duration.ofMillis(100L));

            for (ConsumerRecord<String, PromoEvent> rec : records) {
                log.info("Received a rollback event for a promo {}", rec.key());
                try {
                    service.rollbackPromo(rec.value().id(), rec.value().value())
                            .subscribe(p -> log.info("Promo rollback {}", p.id()));
                } finally {
                    consumer.commitAsync();
                }
            }

        }
    }
}
