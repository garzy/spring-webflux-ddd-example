package com.tangorabox.prices.domain.types;

import lombok.Data;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Data
public class PriceDate {

    private final OffsetDateTime value;

    public static PriceDate valueOf(int year, int month, int day, int hour) {
        return new PriceDate(OffsetDateTime.of(year, month, day, hour, 0, 0, 0, ZoneOffset.UTC));
    }
}
