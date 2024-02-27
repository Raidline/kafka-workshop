package com.ctw.summit.promo.service;

import com.ctw.summit.promo.handler.PromoHandler;
import com.ctw.summit.promo.kafka.PromoEventPublisher;
import com.ctw.summit.promo.model.Promo;
import com.ctw.summit.promo.repo.PromoRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PromoService {

    private final PromoRepo repo;
    private final PromoEventPublisher eventPublisher;

    public Mono<Promo> findPromo(int id) {
        return repo.findById(id);
    }

    public Mono<Promo> updatePromo(int id, PromoHandler.PromoUpdate update) {
        return this.findPromo(id)
                .map(p -> new Promo(
                        p.id(),
                        update.option(),
                        p.productId(),
                        p.options()
                ))
                .flatMap(repo::save)
                .doOnNext(p -> eventPublisher.sendEvent(p.id(), p.value()));
    }

    public Flux<Promo> getAllPromos() {
        return repo.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    public Mono<Promo> rollbackPromo(int id, int value) {
        return this.findPromo(id)
                .map(p -> new Promo(
                        p.id(),
                        value,
                        p.productId(),
                        p.options()
                ))
                .flatMap(repo::save);
    }
}
