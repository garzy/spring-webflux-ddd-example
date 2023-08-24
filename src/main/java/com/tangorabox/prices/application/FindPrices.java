package com.tangorabox.prices.application;

import com.tangorabox.prices.domain.entity.Price;
import com.tangorabox.prices.domain.entity.PriceRepository;
import com.tangorabox.prices.domain.entity.PriceRequest;
import com.tangorabox.prices.domain.entity.PriceResponse;
import com.tangorabox.prices.domain.mappers.PriceMapper;
import lombok.AllArgsConstructor;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class FindPrices {

    private final PriceRepository priceRepository;
    private final PriceMapper priceMapper;

    public Optional<PriceResponse> execute(PriceRequest request) {
        final List<Price> prices = priceRepository.find(request);
        if (prices.isEmpty()) {
            return Optional.empty();
        }

        return prices.stream().max(Comparator.comparingInt(Price::getPriority)).map(priceMapper::toPriceResponse);
    }
}
