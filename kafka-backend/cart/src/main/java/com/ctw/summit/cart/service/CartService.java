package com.ctw.summit.cart.service;

import com.ctw.summit.cart.kafka.ProductEvent;
import com.ctw.summit.cart.model.CartItem;
import com.ctw.summit.cart.repo.CartRepo;
import com.ctw.summit.cart.repo.CartRepo.Product;
import com.ctw.summit.cart.repo.PromoRepo;
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

    public Mono<Void> addProduct(ProductEvent prod) {
        var promoForProd = promoRepo.findByProductId(prod.id())
                .map(p -> new Product(prod.id(), prod.name(), prod.price() - p.value()))
                .switchIfEmpty(Mono.just(new Product(prod.id(), prod.name(), prod.price())));

        return promoForProd.flatMap(cartRepo::addProduct);
    }

    public Mono<CartItem> updateProductPrice(int prodId, int newValue) {
        return cartRepo.updateProdPrice(prodId, newValue);
    }

    public Mono<Void> clear() {
        return cartRepo.clear();
    }
}
