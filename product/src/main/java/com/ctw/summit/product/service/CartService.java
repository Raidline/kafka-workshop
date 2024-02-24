package com.ctw.summit.product.service;

import com.ctw.summit.product.model.CartItem;
import com.ctw.summit.product.model.Product;
import com.ctw.summit.product.repo.CartRepo;
import com.ctw.summit.product.repo.PromoRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepo cartRepo;
    private final PromoRepo promoRepo;

    public Flux<CartItem> getCart() {
        return cartRepo.getCart();
    }

    public Mono<Void> addProduct(Product prod) {
        var promoForProd = promoRepo.findByProductId(prod.id())
                .map(p -> new Product(prod.id(), prod.name(), prod.value() - p.value(), prod.related()))
                .switchIfEmpty(Mono.just(prod));

        return promoForProd.flatMap(cartRepo::addProduct);
    }

    public Mono<CartItem> updateProductPrice(int prodId, int newValue) {
        return cartRepo.updateProdPrice(prodId, newValue);
    }

    public Mono<Void> clear() {
        return cartRepo.clear();
    }
}
