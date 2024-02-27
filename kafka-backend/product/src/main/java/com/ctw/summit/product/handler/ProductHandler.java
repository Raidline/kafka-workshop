package com.ctw.summit.product.handler;

import com.ctw.summit.product.service.CartService;
import com.ctw.summit.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductHandler {

    private final ProductService service;
    private final CartService cartService;

    public Mono<ServerResponse> getProductsWithPromo(ServerRequest serverRequest) {
        var res = service.getAllProducts();


        return ServerResponse.ok()
                .body(res, new ParameterizedTypeReference<>() {
                });
    }

    public Mono<ServerResponse> getCart(ServerRequest serverRequest) {
        var res = cartService.getCart()
                .map(item -> new CartResponse(item.name(), item.value()));

        return ServerResponse.ok()
                .body(res, new ParameterizedTypeReference<>(){});
    }

    public Mono<ServerResponse> addProductToCart(ServerRequest serverRequest) {
        var prodId = serverRequest.queryParam("id")
                .orElseThrow(() -> new IllegalArgumentException("Missing id"));

        var product = service.findById(Integer.parseInt(prodId));

        return product.flatMap(cartService::addProduct)
                .then(ServerResponse.accepted()
                        .build());
    }

    public Mono<ServerResponse> clearCart(ServerRequest serverRequest) {
        return cartService.clear()
                .then(ServerResponse.accepted()
                        .build());
    }


    public record CartResponse(String prodName, int value) {}
}
