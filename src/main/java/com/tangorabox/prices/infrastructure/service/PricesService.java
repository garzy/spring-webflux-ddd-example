package com.tangorabox.prices.infrastructure.service;

import com.tangorabox.prices.application.FindPrices;
import com.tangorabox.prices.domain.models.PriceRequest;
import com.tangorabox.prices.domain.models.PriceResponse;
import com.tangorabox.prices.domain.types.PriceDate;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@Slf4j
@AllArgsConstructor
public class PricesService {

    private final FindPrices findPrices;

    public Mono<PriceResponse> findCurrentPrice(LocalDateTime date, long productId, int brandId) {
        return findPrices.execute(PriceRequest.builder().date(PriceDate.of(date)).productId(productId).brandId(brandId).build())
                .doOnNext(price -> log.debug("Founded Price: {}", price));
    }
}
