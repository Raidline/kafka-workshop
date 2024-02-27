package com.ctw.summit.promo.handler;

import com.ctw.summit.promo.model.Promo;
import com.ctw.summit.promo.service.PromoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class PromoHandler {

    private final PromoService service;

    public Mono<ServerResponse> getPromo(ServerRequest serverRequest) {
        var promo = service.findPromo(Integer.parseInt(serverRequest.pathVariable("id")));

        return ServerResponse.ok()
                .body(promo, new ParameterizedTypeReference<>() {});
    }

    public Mono<ServerResponse> updatePromo(ServerRequest serverRequest) {
        var body = serverRequest.bodyToMono(PromoUpdate.class);
        var id = Integer.parseInt(serverRequest.pathVariable("id"));

        var promo = body.flatMap(p -> service.updatePromo(id, p));

        return ServerResponse.ok()
                .body(promo, Promo.class);
    }

    public Mono<ServerResponse> getAllPromos(ServerRequest serverRequest) {
        return ServerResponse.ok()
                .body(service.getAllPromos(), new ParameterizedTypeReference<>() {});
    }


    public record PromoUpdate(
            int option
    ) {}
}
