package com.ctw.summit.promo.configuration;

import com.ctw.summit.promo.kafka.PromoEventSerializer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KafkaConfiguration {

    @Bean
    public Properties kafkaProperties() {
        var props = new Properties();
        props.put("bootstrap.servers", "localhost:29092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, PromoEventSerializer.class);
        props.put(ProducerConfig.MAX_REQUEST_SIZE_CONFIG, 20971520);

        return props;
    }
}
