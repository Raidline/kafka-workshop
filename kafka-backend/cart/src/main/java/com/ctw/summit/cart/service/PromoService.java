package com.ctw.summit.cart.service;

import com.ctw.summit.cart.kafka.PromoEvent;
import com.ctw.summit.cart.model.Promo;
import com.ctw.summit.cart.repo.PromoRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class PromoService {

    private final CartService cartService;
    private final PromoRepo promoRepo;

    public void updatePromo(PromoEvent value, boolean forceError) {

        promoRepo.findById(value.id())
                .flatMap(p -> {
                    if (forceError) {
                        return Mono.error(new PromoException("Some error on DB", p.id(), p.value()));
                    }
                    return promoRepo.save(new Promo(p.id(), value.value(), p.productId(), p.options()));
                })
                .flatMap(prod ->
                        cartService.updateProductPrice(prod.id(), prod.value() - value.value()))
                .subscribe(x -> log.info("Promo updated {}", x.name()), th -> log.error(th.getMessage()));
    }


    public static class PromoException extends RuntimeException {

        public final int id;
        public final int value;

        public PromoException(String message, int id, int value) {
            super(message);
            this.id = id;
            this.value = value;
        }
    }
}
