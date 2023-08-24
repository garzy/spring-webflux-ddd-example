package com.tangorabox.prices.domain.entity;

import com.tangorabox.prices.domain.types.PVP;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class PriceResponse {
    private final long productId;
    private final int brandId;
    private final int rate;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private PVP salePrice;
}
