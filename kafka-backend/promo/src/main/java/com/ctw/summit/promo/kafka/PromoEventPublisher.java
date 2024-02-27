package com.ctw.summit.promo.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
@RequiredArgsConstructor
public class PromoEventPublisher {

    private static final String PROMO_TOPIC = "promo.update";

    private final Properties kafkaProperties;

    @Async
    public void sendEvent(int promoId, int value) {
        try (var producer = new KafkaProducer<String, PromoEvent>(kafkaProperties)) {
            producer.send(new ProducerRecord<>(
                    PROMO_TOPIC,
                    String.valueOf(promoId),
                    new PromoEvent(promoId, value)
            ));
        }
    }
}
