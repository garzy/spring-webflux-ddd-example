package com.tangorabox.prices.domain.entity;

import java.util.List;

public interface PriceRepository {

    List<Price> find(PriceRequest request);
}
