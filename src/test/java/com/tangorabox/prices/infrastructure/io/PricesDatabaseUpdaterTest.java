package com.tangorabox.prices.infrastructure.io;

import com.tangorabox.prices.infrastructure.entity.PriceEntity;
import com.tangorabox.prices.infrastructure.mappers.PriceMapperInfra;
import com.tangorabox.prices.infrastructure.repository.PriceRepositoryJPA;
import com.tangorabox.prices.infrastructure.service.PricesDatabaseUpdater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PricesDatabaseUpdaterTest extends CSVFileTest {

    @Mock
    private PriceRepositoryJPA priceRepository;

    private CSVReactiveReader csvReactiveReader;

    @BeforeEach
    public void beforeTest() {
        csvReactiveReader = new CSVReaderOpenCSV();
    }

    @Test
    public void test() throws IOException {
        //Given
        PricesDatabaseUpdater updater = new PricesDatabaseUpdater(priceRepository, csvReactiveReader, Mappers.getMapper(PriceMapperInfra.class));
        List<PriceEntity> defaultDatabasePrices = generateDatabaseDefaults();

        given(priceRepository.findById(anyLong())).willReturn(Mono.empty());
        given(priceRepository.findAll()).willReturn(Flux.fromIterable(defaultDatabasePrices));
        given(priceRepository.deleteAll(any(Publisher.class))).willReturn(Mono.empty());
        given(priceRepository.saveAll(ArgumentMatchers.<Publisher<PriceEntity>>any())).willAnswer(invocationOnMock -> {
            Publisher<PriceEntity> publisher = invocationOnMock.getArgument(0, Publisher.class);
            StepVerifier.create(publisher)
                    .expectNextCount(NUM_ENTRIES_IN_FILE)
                    .verifyComplete();
            return Flux.empty();
        });

        //When
        updater.update(CSV_PATH);

        //Then
        verify(priceRepository, times(NUM_ENTRIES_IN_FILE)).findById(anyLong());
        verify(priceRepository, atLeastOnce()).saveAll(any(Publisher.class));
        verify(priceRepository, atLeastOnce()).deleteAll(any(Publisher.class));

    }

    private List<PriceEntity> generateDatabaseDefaults() {
        List<PriceEntity> result = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            PriceEntity price = new PriceEntity();
            price.setId((long) i);
            result.add(price);
        }
        return result;
    }
}
