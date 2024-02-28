package com.ctw.summit.cart.service;

import com.ctw.summit.cart.handler.CartHandler;
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

    public Flux<CartItem> getCart() {
        return cartRepo.getCart();
    }

    public Mono<Void> addProduct(CartHandler.CartRequest request) {
        return cartRepo.addProduct(
                new Product(request.id(), request.name(), request.value()));
    }

    public Mono<CartItem> updateProductPrice(int prodId, int newValue) {
        return cartRepo.updateProdPrice(prodId, newValue);
    }

    public Mono<Void> clear() {
        return cartRepo.clear();
    }
}
