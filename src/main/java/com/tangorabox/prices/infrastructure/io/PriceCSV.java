package com.tangorabox.prices.infrastructure.io;

import com.opencsv.bean.CsvDate;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
public class PriceCSV {

    private long brandId;
    /**
     * start date in which the indicated rate price applies
     */
    @CsvDate("yyyy-MM-dd-HH.mm.ss")
    private LocalDateTime startDate;
    /**
     * end date in which the indicated rate price applies
     */
    @CsvDate("yyyy-MM-dd-HH.mm.ss")
    private LocalDateTime endDate;
    /**
     * Identifier of the applicable price list.
     */
    private int rate;
    /**
     * Product code identifier
     */
    private long productId;
    /**
     * Pricing enforcer stripper. If two rates coincide in a range of dates, the one with the highest priority (highest numerical value) is applied.
     */
    private int priority;
    /**
     * final sale price.
     */
    private BigDecimal salePrice;
    /**
     * Currency ISO Code
     */
    private String currency;
}
