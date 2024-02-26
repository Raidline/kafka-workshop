package com.ctw.summit.promo.router;

import com.ctw.summit.promo.handler.PromoHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class PromoRouter {

    @Bean
    public RouterFunction<ServerResponse> router(PromoHandler handler) {
        return route()
                .GET("", handler::getAllPromos)
                .GET("{id}", handler::getPromo)
                .PUT("{id}", handler::updatePromo)
                .build();
    }
}
