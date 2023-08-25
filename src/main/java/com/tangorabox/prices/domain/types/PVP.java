package com.tangorabox.prices.domain.types;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PVP {
    private final BigDecimal amount;

    public static PVP of(BigDecimal amount) {
        return new PVP(amount);
    }
}