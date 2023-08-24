package com.tangorabox.prices.domain.entity;

import com.tangorabox.prices.domain.types.Currency;
import com.tangorabox.prices.domain.types.PVP;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class Price {
    private int brandId;
    private OffsetDateTime startDate;
    private OffsetDateTime endDate;
    private int rate;
    private long productId;
    private int priority;
    private PVP salePrice;
    private Currency currency;
}
