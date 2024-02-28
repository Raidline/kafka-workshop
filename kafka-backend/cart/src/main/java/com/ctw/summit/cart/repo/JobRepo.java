package com.ctw.summit.cart.repo;

import com.ctw.summit.cart.model.JobRecord;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepo extends ReactiveCrudRepository<JobRecord, Integer> {
}
