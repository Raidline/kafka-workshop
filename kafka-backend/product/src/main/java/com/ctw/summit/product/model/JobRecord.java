package com.ctw.summit.product.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(name = "job_record")
public record JobRecord(
        @Id
        int id,
        String jobName,
        String jobStatus,
        int wantedValue,
        int productId
) {
}
