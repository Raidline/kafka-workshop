package com.ctw.summit.promo.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PromoDeserializer implements Deserializer<PromoEvent> {

    private static final Logger log = LoggerFactory.getLogger(PromoDeserializer.class);

    private final ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();

    @Override
    public PromoEvent deserialize(String topic, byte[] data) {
        PromoEvent promo = null;

        try {
            if (data.length == 0) {
                return null;
            }

            promo = mapper.readValue(data, PromoEvent.class);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return promo;
    }
}
