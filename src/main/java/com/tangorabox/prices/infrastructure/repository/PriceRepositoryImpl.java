package com.tangorabox.prices.infrastructure.repository;

import com.tangorabox.prices.domain.models.Price;
import com.tangorabox.prices.domain.models.PriceRepository;
import com.tangorabox.prices.domain.models.PriceRequest;
import com.tangorabox.prices.infrastructure.mappers.PriceMapperInfra;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@AllArgsConstructor
public class PriceRepositoryImpl implements PriceRepository {

    private final PriceRepositoryJPA priceRepositoryJPA;
    private final PriceMapperInfra priceMapperInfra;

    public Flux<Price> find(PriceRequest request) {
        return priceRepositoryJPA.findAllByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual
                        (request.getProductId(), request.getBrandId(), request.getDate().getValue(), request.getDate().getValue())
                .map(priceMapperInfra::toPrice);
    }
}
