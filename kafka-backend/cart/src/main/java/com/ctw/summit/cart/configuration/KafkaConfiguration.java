package com.ctw.summit.cart.configuration;

import com.ctw.summit.cart.kafka.PromoDeserializer;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;
import java.util.UUID;

import static org.apache.kafka.clients.consumer.ConsumerConfig.GROUP_INSTANCE_ID_CONFIG;

@Configuration
public class KafkaConfiguration {

    @Bean
    public Properties kafkaProperties() {
        var props = new Properties();
        props.put("bootstrap.servers", "localhost:29092");
        props.put("enable.auto.commit", "false");
        props.put(GROUP_INSTANCE_ID_CONFIG, ("SchedulerCoordinator"+ UUID.randomUUID()));
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, PromoDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "store-group");
        props.put("auto.offset.reset", "latest");

        return props;
    }
}
