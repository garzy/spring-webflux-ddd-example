package com.tangorabox.prices.infrastructure.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Table("PRICES")
public class PriceEntity {

    @Id
    private Long id;
    private long brandId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private int rate;
    private long productId;
    private int priority;
    private BigDecimal salePrice;
    private String currency;
}
