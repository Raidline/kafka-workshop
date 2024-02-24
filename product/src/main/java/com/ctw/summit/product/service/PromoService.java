package com.ctw.summit.product.service;

import com.ctw.summit.product.kafka.PromoEvent;
import com.ctw.summit.product.model.Promo;
import com.ctw.summit.product.repo.PromoRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class PromoService {

    private final CartService cartService;
    private final ProductService productService;
    private final PromoRepo promoRepo;

    public void updatePromo(PromoEvent value, boolean forceError) {

        promoRepo.findById(value.id())
                .map(p -> new Promo(p.id(), value.value(), p.productId(), p.options()))
                .flatMap(entity -> {
                    if (forceError) {
                        return Mono.error(new IllegalStateException("Some error on DB"));
                    }
                    return promoRepo.save(entity);
                })
                .flatMap(p -> productService.findById(p.productId())
                        .flatMap(prod ->
                                cartService.updateProductPrice(prod.id(), prod.value() - p.value()))
                )
                .subscribe(x -> log.info("Promo updated {}", x.name()), th -> log.error(th.getMessage()));
    }
}
