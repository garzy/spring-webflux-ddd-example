package com.tangorabox.prices.domain.models;

import com.tangorabox.prices.domain.types.Currency;
import com.tangorabox.prices.domain.types.PVP;
import com.tangorabox.prices.domain.types.PriceDate;
import lombok.Data;

@Data
public class Price {
    private int brandId;
    private PriceDate startDate;
    private PriceDate endDate;
    private int rate;
    private long productId;
    private int priority;
    private PVP salePrice;
    private Currency currency;
}
