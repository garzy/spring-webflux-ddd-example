package com.tangorabox.prices.infrastructure.controller;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PriceResponseDTO {

    private long productId;
    private long brandId;
    private int rate;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal salePrice;
}
