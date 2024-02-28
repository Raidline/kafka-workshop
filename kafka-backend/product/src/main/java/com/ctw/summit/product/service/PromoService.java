package com.ctw.summit.product.service;

import com.ctw.summit.product.kafka.PromoEvent;
import com.ctw.summit.product.kafka.PromoEventPublisher;
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

    private final PromoEventPublisher promoEventPublisher;
    private final ProductService productService;
    private final PromoRepo promoRepo;

    public void updatePromo(PromoEvent value, boolean forceError) {

        promoRepo.findById(value.id())
                .flatMap(p -> {
                    if (forceError) {
                        return Mono.error(new PromoException("Some error on DB", p.id(), p.value()));
                    }
                    return promoRepo.save(new Promo(p.id(), value.value(), p.productId(), p.options()));
                })
                .flatMap(p -> productService.findById(p.productId()))
                .subscribe(x -> log.info("Promo updated {}", x.name()), th -> {
                    log.error(th.getMessage());

                    var promoException = (PromoException) th;

                    promoEventPublisher.sendEvent(promoException.id, promoException.value);
                });
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
