package com.tangorabox.prices.domain.types;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PVP {
    private final BigDecimal amount;
}