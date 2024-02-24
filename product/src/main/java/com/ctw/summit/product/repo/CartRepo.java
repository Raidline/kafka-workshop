package com.ctw.summit.product.repo;

import com.ctw.summit.product.model.CartItem;
import com.ctw.summit.product.model.Product;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class CartRepo {

    private static final int CART_ID = 1;

    private final ConcurrentMap<Integer, ArrayList<CartItem>> cart = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        this.cart.put(1, new ArrayList<>());
    }

    public Mono<Void> addProduct(Product product) {
        return Mono.just(new CartItem(product.id(), product.name(), product.value()))
                .publishOn(Schedulers.boundedElastic())
                .doOnNext(c -> {
                    var items = cart.get(CART_ID);
                    synchronized (items) {

                        items.add(c);

                        cart.put(CART_ID, items);
                    }
                }).then();
    }

    public Flux<CartItem> getCart() {
        return Flux.fromIterable(cart.values().stream().flatMap(Collection::stream).toList())
                .delayElements(Duration.ofMillis(10));
    }

    public Mono<Void> clear() {
        return Mono.fromRunnable(cart::clear);
    }

    public Mono<CartItem> updateProdPrice(int prodId, int newValue) {
        return Mono.fromCallable(() -> {
            var items = cart.get(CART_ID);

            CartItem removable = null;
            CartItem updatable = null;
            for (CartItem item : items) {
                if (item.prodId() == prodId) {
                    updatable = new CartItem(prodId, item.name(), newValue);
                    removable = item;

                    break;
                }
            }

            if (updatable != null) {
                items.remove(removable);
                items.add(updatable);
            }

            return updatable;
        });
    }
}
