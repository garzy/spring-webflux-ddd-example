package com.tangorabox.prices.domain.models;

import reactor.core.publisher.Flux;

public interface PriceRepository {

    Flux<Price> find(PriceRequest request);
}
