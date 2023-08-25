package com.tangorabox.prices.domain.models;

import com.tangorabox.prices.domain.types.PriceDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PriceRequest {
    private final PriceDate date;
    private final long productId;
    private final int brandId;
}
