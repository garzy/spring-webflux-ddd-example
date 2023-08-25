package com.tangorabox.prices.infrastructure.service;

import com.tangorabox.prices.infrastructure.entity.PriceEntity;
import com.tangorabox.prices.infrastructure.io.CSVReactiveReader;
import com.tangorabox.prices.infrastructure.io.PriceCSV;
import com.tangorabox.prices.infrastructure.mappers.PriceMapperInfra;
import com.tangorabox.prices.infrastructure.repository.PriceRepositoryJPA;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

@Service
@Slf4j
@AllArgsConstructor
public class PricesDatabaseUpdater {

    private final PriceRepositoryJPA priceRepository;
    private final CSVReactiveReader csvReactiveReader;
    private final PriceMapperInfra priceMapper;

    public void update(Path csvPath) throws IOException {
        log.debug("Starting Update...");

        final Set<Long> pricesInCSVFileAsSet = new HashSet<>();

        final Flux<PriceEntity> pricesInCSVFile = getPricesInCSVFile(csvPath)
                .doOnNext(price -> pricesInCSVFileAsSet.add(price.getId()))
                .doOnComplete(() -> deletePricesThatAreNotInSet(pricesInCSVFileAsSet))
                .doOnComplete(() -> log.debug("End Update"));

        Flux<PriceEntity> pricesToUpdateOrInsert = getPricesToUpdateOrInsert(pricesInCSVFile);

        priceRepository.saveAll(pricesToUpdateOrInsert).then().subscribe();
    }

    private Flux<PriceEntity> getPricesInCSVFile(Path csvPath) throws IOException {
        return csvReactiveReader
                .readFile(csvPath)
                .zipWith(Flux.range(1, Integer.MAX_VALUE))
                .doOnNext(objects -> log.debug("Incoming PriceCSV: (id:{}) {})", objects.getT2(), objects.getT1()))
                .map(this::updatePriceIDWithRowNumber)
                .doOnNext(price -> log.debug("Mapped Price: {}", price));
    }

    private PriceEntity updatePriceIDWithRowNumber(Tuple2<PriceCSV, Integer> objects) {
        PriceEntity price = priceMapper.toPriceEntity(objects.getT1());
        price.setId(objects.getT2().longValue());
        return price;
    }

    private Flux<PriceEntity> getPricesToUpdateOrInsert(Flux<PriceEntity> pricesInCSVFile) {
        return pricesInCSVFile.flatMap(csvPrice ->
                priceRepository
                        .findById(csvPrice.getId())
                        .map(p -> csvPrice) //change to more recent entity
                        .switchIfEmpty(newEntityForInsert(csvPrice))
                        .doOnNext(p -> log.debug("Price Will Be Saved: {}", p)));
    }

    private void deletePricesThatAreNotInSet(Set<Long> pricesInCSVFileAsSet) {
        final Flux<PriceEntity> pricesToRemove = priceRepository.findAll().filter(notInSet(pricesInCSVFileAsSet));
        priceRepository.deleteAll(pricesToRemove).then().subscribe();
    }

    private static Mono<PriceEntity> newEntityForInsert(PriceEntity csvPrice) {
        return Mono.just(csvPrice).map(p -> {
            p.setId(null);
            return p;
        });
    }

    private static Predicate<PriceEntity> notInSet(Set<Long> pricesInCSVFileAsSet) {
        return price -> !pricesInCSVFileAsSet.contains(price.getId());
    }
}
