package com.tangorabox.prices.testutils;


import com.tangorabox.prices.infrastructure.entity.PriceEntity;
import com.tangorabox.prices.infrastructure.io.CSVFileTest;
import com.tangorabox.prices.infrastructure.io.PriceCSV;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;

public class AssertionUtils {

    private AssertionUtils() {
        super();
    }

    public static void assertValuesCSV(Collection<PriceCSV> prices) {
        assertThat(prices).hasSize(CSVFileTest.NUM_ENTRIES_IN_FILE);
        for (PriceCSV price : prices) {
            assertThat(price.getBrandId()).isEqualTo(1);
            assertDate(price.getStartDate());
            assertDate(price.getEndDate());
            assertThat(price.getRate()).isIn(1, 2, 3, 4);
            assertThat(price.getProductId()).isEqualTo(35455);
            assertThat(price.getPriority()).isIn(0, 1);
            assertThat(price.getSalePrice()).isGreaterThan(BigDecimal.valueOf(20));
            assertThat(price.getCurrency()).isEqualTo("EUR");
        }

    }

    private static void assertDate(LocalDateTime dateTime) {
        assertThat(dateTime).isNotNull();
        assertThat(dateTime.getYear()).isEqualTo(2020);
    }

    public static void assertValues(Collection<PriceEntity> prices) {
        assertThat(prices).hasSize(CSVFileTest.NUM_ENTRIES_IN_FILE);
        for (PriceEntity price : prices) {
            assertThat(price.getBrandId()).isEqualTo(1);
            assertDate(price.getStartDate());
            assertDate(price.getEndDate());
            assertThat(price.getRate()).isIn(1, 2, 3, 4);
            assertThat(price.getProductId()).isEqualTo(35455);
            assertThat(price.getPriority()).isIn(0, 1);
            assertThat(price.getSalePrice()).isGreaterThan(BigDecimal.valueOf(20));
            assertThat(price.getCurrency()).isEqualTo("EUR");
        }

    }
}
