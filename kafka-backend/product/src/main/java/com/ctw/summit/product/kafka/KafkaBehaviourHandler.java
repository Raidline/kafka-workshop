package com.ctw.summit.product.kafka;

import com.ctw.summit.product.kafka.PromoConsumer.ConsumerState;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class KafkaBehaviourHandler {

    private final PromoConsumer promoConsumer;

    public Mono<ServerResponse> listen(ServerRequest serverRequest) {
        try {
            promoConsumer.chanceConfig(ConsumerState.OK);

            return ServerResponse.accepted()
                    .build();
        } catch (Exception e) {
            return ServerResponse.status(HttpStatusCode.valueOf(500))
                    .build();
        }
    }

    public Mono<ServerResponse> enableNok(ServerRequest serverRequest) {
        try {
            promoConsumer.chanceConfig(ConsumerState.NOK);

            return ServerResponse.accepted()
                    .build();
        } catch (Exception e) {
            return ServerResponse.status(HttpStatusCode.valueOf(500))
                    .build();
        }
    }

    public Mono<ServerResponse> disconnect(ServerRequest serverRequest) {
        try {
            promoConsumer.chanceConfig(ConsumerState.DISCONNECT);

            return ServerResponse.accepted()
                    .build();
        } catch (Exception e) {
            return ServerResponse.status(HttpStatusCode.valueOf(500))
                    .build();
        }
    }

    public Mono<ServerResponse> status(ServerRequest serverRequest) {
        try {
            return ServerResponse.ok()
                    .bodyValue(promoConsumer.getStatus());
        } catch (Exception e) {
            return ServerResponse.status(HttpStatusCode.valueOf(500))
                    .build();
        }
    }
}
