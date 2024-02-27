package com.ctw.summit.product.repo;

import com.ctw.summit.product.model.Promo;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface PromoRepo extends ReactiveCrudRepository<Promo, Integer> {

    Mono<Promo> findByProductId(int productId);
}
