package com.tangorabox.prices.application;

import com.tangorabox.prices.domain.mappers.PriceMapper;
import com.tangorabox.prices.domain.models.PriceRepository;
import com.tangorabox.prices.domain.models.PriceRequest;
import com.tangorabox.prices.domain.models.PriceResponse;
import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class FindPrices {

    private final PriceRepository priceRepository;
    private final PriceMapper priceMapper;

    public Mono<PriceResponse> execute(PriceRequest request) {
        return priceRepository.find(request)
                .reduce((maxPriority, currentObj) -> maxPriority.getPriority() > currentObj.getPriority() ? maxPriority : currentObj)
                .map(priceMapper::toPriceResponse);
    }
}
