package com.ctw.summit.cart;

import com.ctw.summit.cart.repo.JobRepo;
import com.ctw.summit.cart.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartStartupListener implements ApplicationListener<ApplicationReadyEvent> {

    private final JobRepo jobRepo;
    private final CartService cartService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        jobRepo.findAll()
                .flatMap(j -> cartService.updateProductPrice(j.productId(), j.wantedValue()))
                .subscribe();
    }
}
