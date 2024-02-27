package com.ctw.summit.promo.repo;

import com.ctw.summit.promo.model.Promo;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PromoRepo extends ReactiveCrudRepository<Promo, Integer>, ReactiveSortingRepository<Promo, Integer> {
}
