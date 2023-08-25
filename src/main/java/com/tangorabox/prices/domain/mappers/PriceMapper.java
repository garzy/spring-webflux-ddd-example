package com.tangorabox.prices.domain.mappers;

import com.tangorabox.prices.domain.models.Price;
import com.tangorabox.prices.domain.models.PriceResponse;
import org.mapstruct.Mapper;

@Mapper
public interface PriceMapper {

    PriceResponse toPriceResponse(Price price);
}
