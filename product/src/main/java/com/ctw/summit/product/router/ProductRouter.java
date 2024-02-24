package com.ctw.summit.product.router;

import com.ctw.summit.product.handler.ProductHandler;
import com.ctw.summit.product.kafka.KafkaBehaviourHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ProductRouter {

    @Bean
    public RouterFunction<ServerResponse> router(ProductHandler handler, KafkaBehaviourHandler kafkaHandler) {
        return route()
                .path("kafka", b -> b.nest(__ -> true,
                        builder -> builder
                                .GET("status", kafkaHandler::status)
                                .POST("ok", kafkaHandler::listen)
                                .POST("nok", kafkaHandler::enableNok)
                                .POST("disconnect", kafkaHandler::disconnect)))
                .GET("promo", handler::getProductsWithPromo)
                .GET("cart", handler::getCart)
                .POST("add", handler::addProductToCart)
                .POST("clear", handler::clearCart)
                .build();
    }
}
