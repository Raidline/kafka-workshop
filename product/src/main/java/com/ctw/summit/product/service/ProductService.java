package com.ctw.summit.product.service;

import com.ctw.summit.product.model.Product;
import com.ctw.summit.product.model.Promo;
import com.ctw.summit.product.repo.ProductRepo;
import com.ctw.summit.product.repo.PromoRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;
    private final PromoRepo promoRepo;

    public Flux<ProductPromo> getAllProducts() {
        var promos = promoRepo.findAll().collectMap(Promo::productId);
        var products = productRepo.findAll();

        return promos
                .flatMapMany(promoMap ->
                        products.map(product -> {
                            var promo = promoMap.get(product.id());
                            return new ProductPromo(
                                    product.id(),
                                    product.name(),
                                    product.value(),
                                    promo != null ? product.value() - promo.value() : product.value(),
                                    product.related()
                            );
                        }));
    }

    public Mono<Product> findById(int prodId) {
        return productRepo.findById(prodId);
    }


    public record ProductPromo(
            int prodId,
            String prodName,
            double originalValue,
            double finalValue,
            int[] related
    ) {
    }
}
