package com.tangorabox.prices.infrastructure.repository;

import com.tangorabox.prices.infrastructure.entity.PriceEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Repository
public interface PriceRepositoryJPA extends ReactiveCrudRepository<PriceEntity, Integer> {

    Flux<PriceEntity> findAllByProductIdAndBrandIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(long productId, long brandId, LocalDateTime date, LocalDateTime date2);


}
