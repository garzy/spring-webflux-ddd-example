package com.tangorabox.prices.infrastructure.controller;

import com.tangorabox.prices.infrastructure.mappers.PriceMapperInfra;
import com.tangorabox.prices.infrastructure.service.PricesService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@RestController
@RequestMapping("prices")
@AllArgsConstructor
public class PricesController {

    private final PricesService pricesService;
    private final PriceMapperInfra mapper;

    @GetMapping
    public Mono<ResponseEntity<PriceResponseDTO>> findPrice(@RequestParam
                                                            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                                            LocalDateTime date,
                                                            @RequestParam long productId,
                                                            @RequestParam int brandId) {
        return pricesService.findCurrentPrice(date, productId, brandId)
                .map(mapper::toPriceResponseDTO)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
