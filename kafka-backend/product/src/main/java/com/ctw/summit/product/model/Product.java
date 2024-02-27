package com.ctw.summit.product.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "product")
public record Product(
        @Id
        int id,
        String name,
        int value,
        int[] related
) {}
