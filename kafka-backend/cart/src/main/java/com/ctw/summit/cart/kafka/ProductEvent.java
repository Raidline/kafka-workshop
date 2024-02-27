package com.ctw.summit.cart.kafka;

public record ProductEvent(
        int id,
        String name,
        int price
) {
}
