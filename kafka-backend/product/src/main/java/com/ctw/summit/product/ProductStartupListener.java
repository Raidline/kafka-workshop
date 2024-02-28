package com.ctw.summit.product;

import com.ctw.summit.product.repo.JobRepo;
import com.ctw.summit.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductStartupListener implements ApplicationListener<ApplicationReadyEvent> {

    private final JobRepo jobRepo;
    private final ProductService productService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        jobRepo.findAll()
                .flatMap(j -> productService.updateProduct(j.productId(), j.wantedValue()))
                .flatMap(__ -> jobRepo.deleteAll())
                .subscribe();
    }
}
