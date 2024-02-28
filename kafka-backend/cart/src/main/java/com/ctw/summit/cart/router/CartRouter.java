package com.ctw.summit.cart.router;

import com.ctw.summit.cart.handler.CartHandler;
import com.ctw.summit.cart.kafka.KafkaBehaviourHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class CartRouter {

    @Bean
    public RouterFunction<ServerResponse> router(CartHandler handler,
                                                 KafkaBehaviourHandler kafkaHandler) {
        return route()
                .path("cart", b -> b.nest(__ -> true,
                        builder -> builder
                                .GET("status", kafkaHandler::status)
                                .POST("ok", kafkaHandler::listen)
                                .POST("nok", kafkaHandler::enableNok)
                                .POST("disconnect", kafkaHandler::disconnect)))
                .GET("cart", handler::getCart)
                .POST("cart", handler::addToCart)
                .POST("clear", handler::clearCart)
                .build();
    }
}
