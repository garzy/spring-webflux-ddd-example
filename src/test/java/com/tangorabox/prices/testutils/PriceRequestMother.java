package com.tangorabox.prices.testutils;

import com.tangorabox.prices.domain.entity.PriceRequest;
import com.tangorabox.prices.domain.types.PriceDate;

public final class PriceRequestMother {

    private static final int YEAR = 2020;
    private static final int MONTH = 6;
    private static final int BRAND_ID = 1;
    private static final long PRODUCT_ID = 35455;

    private PriceRequestMother() {
    }

    public static PriceRequest test1() {
        return PriceRequest.builder()
                .brandId(BRAND_ID)
                .productId(PRODUCT_ID)
                .date(getDate(14, 10))
                .build();
    }

    public static PriceRequest test2() {
        return PriceRequest.builder()
                .brandId(BRAND_ID)
                .productId(PRODUCT_ID)
                .date(getDate(14, 16))
                .build();
    }

    public static PriceRequest test3() {
        return PriceRequest.builder()
                .brandId(BRAND_ID)
                .productId(PRODUCT_ID)
                .date(getDate(14, 21))
                .build();
    }

    public static PriceRequest test4() {
        return PriceRequest.builder()
                .brandId(BRAND_ID)
                .productId(PRODUCT_ID)
                .date(getDate(15, 10))
                .build();
    }

    public static PriceRequest test5() {
        return PriceRequest.builder()
                .brandId(BRAND_ID)
                .productId(PRODUCT_ID)
                .date(getDate(16, 21))
                .build();
    }

    private static PriceDate getDate(int day, int hour) {
        return PriceDate.valueOf(YEAR, MONTH, day, hour);
    }
}
