package com.tangorabox.prices.domain.mappers;

import com.tangorabox.prices.domain.entity.Price;
import com.tangorabox.prices.domain.entity.PriceResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PriceMapper {

    PriceResponse toPriceResponse(Price price);
}
