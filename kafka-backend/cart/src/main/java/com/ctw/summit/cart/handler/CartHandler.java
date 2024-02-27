package com.ctw.summit.cart.handler;

import com.ctw.summit.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CartHandler {

    private final CartService cartService;

    public Mono<ServerResponse> getCart(ServerRequest serverRequest) {
        var res = cartService.getCart()
                .map(item -> new CartResponse(item.name(), item.value()));

        return ServerResponse.ok()
                .body(res, new ParameterizedTypeReference<>(){});
    }

    public Mono<ServerResponse> clearCart(ServerRequest serverRequest) {
        return cartService.clear()
                .then(ServerResponse.accepted()
                        .build());
    }


    public record CartResponse(String prodName, int value) {}
}
