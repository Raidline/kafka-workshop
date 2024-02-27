package com.ctw.summit.cart.model;

public record CartItem(
        int prodId,
        String name,
        int value
) {
}
