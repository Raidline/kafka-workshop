package com.ctw.summit.promo.repo;

import com.ctw.summit.promo.model.Promo;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PromoRepo extends ReactiveCrudRepository<Promo, Integer> {
}
