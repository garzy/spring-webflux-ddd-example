package com.tangorabox.prices.domain.entity;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class PriceRequest {
    private final OffsetDateTime date;
    private final long productId;
    private final int brandId;
}
