package com.ctw.summit.cart.model;

import org.springframework.data.relational.core.mapping.Table;

@Table(name = "job_record")
public record JobRecord(
        int id,
        String jobName,
        String jobStatus,
        int wantedValue
) {
}
