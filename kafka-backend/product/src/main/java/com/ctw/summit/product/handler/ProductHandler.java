package com.ctw.summit.product.handler;

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

    public Mono<ServerResponse> getProductsWithPromo(ServerRequest serverRequest) {
        var res = service.getAllProducts();


        return ServerResponse.ok()
                .body(res, new ParameterizedTypeReference<>() {
                });
    }
}
