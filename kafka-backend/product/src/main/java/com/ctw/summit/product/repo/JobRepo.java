package com.ctw.summit.product.repo;

import com.ctw.summit.product.model.JobRecord;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepo extends ReactiveCrudRepository<JobRecord, Integer>{
}
