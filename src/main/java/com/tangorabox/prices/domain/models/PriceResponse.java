package com.tangorabox.prices.domain.models;

import com.tangorabox.prices.domain.types.PVP;
import com.tangorabox.prices.domain.types.PriceDate;
import lombok.Data;

@Data
public class PriceResponse {
    private final long productId;
    private final int brandId;
    private final int rate;
    private PriceDate startDate;
    private PriceDate endDate;
    private PVP salePrice;
}
