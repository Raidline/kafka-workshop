package com.ctw.summit.cart.kafka;

import com.ctw.summit.cart.service.PromoService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
@RequiredArgsConstructor
@Slf4j
public class PromoConsumer {

    private static final String PROMO_TOPIC = "promo.update";
    private volatile boolean forceError = false;
    private final AtomicBoolean disconnect = new AtomicBoolean(false);
    private final AtomicBoolean reconnect = new AtomicBoolean(false);

    private final Properties kafkaProperties;
    private final PromoService service;

    private final Map<ConsumerState, Runnable> dynamicConfigs = Map.of(
            ConsumerState.OK, () -> {
                reconnect.set(true);
                disconnect.compareAndExchange(true, false);
                forceError = false;
            },
            ConsumerState.NOK, () -> forceError = true,
            ConsumerState.DISCONNECT, () -> {
                disconnect.set(true);
                reconnect.compareAndExchange(true, false);
                forceError = false;
            }
    );

    @PostConstruct
    public void startConsumer() {
        new Thread(this::poolRecord, "Promo-Poller").start();
    }

    private void poolRecord() {
        var consumer = new KafkaConsumer<>(kafkaProperties, new StringDeserializer(),
                new PromoDeserializer());

        consumer.subscribe(List.of(PROMO_TOPIC));

        while (true) {

            if (disconnect.get()) {
                consumer.unsubscribe();
                continue;
            } else if (reconnect.get()) {
                consumer.subscribe(List.of(PROMO_TOPIC));
                reconnect.compareAndExchange(true, false);
            }

            var records = consumer.poll(Duration.ofMillis(100L));

            for (ConsumerRecord<String, PromoEvent> rec : records) {
                log.info("Received an update event for a promo {}", rec.key());
                try {
                    service.updatePromo(rec.value(), this.forceError);
                } finally {
                    consumer.commitAsync();
                }
            }

        }
    }


    public void chanceConfig(ConsumerState state) {
        var config = dynamicConfigs.get(state);

        config.run();
    }

    public ConsumerState getStatus() {
        if (forceError) {
            return ConsumerState.NOK;
        } else if (disconnect.get()) {
            return ConsumerState.DISCONNECT;
        } else {
            return ConsumerState.OK;
        }
    }

    public enum ConsumerState {
        OK,
        NOK,
        DISCONNECT
    }
}
