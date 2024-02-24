package com.ctw.summit.promo.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class PromoEventSerializer implements Serializer<PromoEvent> {

    private final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

    @Override
    public byte[] serialize(String topic, PromoEvent data) {

        try {
            return mapper.writeValueAsString(data).getBytes(StandardCharsets.UTF_8);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            return new byte[0];
        }
    }
}
