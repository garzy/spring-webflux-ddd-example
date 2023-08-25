package com.tangorabox.prices.domain.types;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PriceDate {

    private final LocalDateTime value;

    public static PriceDate of(int year, int month, int day, int hour) {
        return new PriceDate(LocalDateTime.of(year, month, day, hour, 0));
    }

    public static PriceDate of(LocalDateTime localDateTime) {
        return new PriceDate(localDateTime);
    }
}
