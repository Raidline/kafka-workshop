package com.ctw.summit.cart.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "promo")
public record Promo(
        @Id
        int id,
        int value,
        int productId,
        int[] options
) {
}
