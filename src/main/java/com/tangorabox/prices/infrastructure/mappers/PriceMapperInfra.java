package com.tangorabox.prices.infrastructure.mappers;

import com.tangorabox.prices.domain.models.Price;
import com.tangorabox.prices.domain.models.PriceResponse;
import com.tangorabox.prices.domain.types.PVP;
import com.tangorabox.prices.domain.types.PriceDate;
import com.tangorabox.prices.infrastructure.controller.PriceResponseDTO;
import com.tangorabox.prices.infrastructure.entity.PriceEntity;
import org.mapstruct.Mapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public abstract class PriceMapperInfra {

    public abstract Price toPrice(PriceEntity priceEntity);

    public abstract PriceResponseDTO toPriceResponseDTO(PriceResponse response);

    PriceDate map(LocalDateTime date) {
        return PriceDate.of(date);
    }

    LocalDateTime map(PriceDate date) {
        return date.getValue();
    }

    PVP map(BigDecimal amount) {
        return PVP.of(amount);
    }

    BigDecimal map(PVP amount) {
        return amount.getAmount();
    }


}
