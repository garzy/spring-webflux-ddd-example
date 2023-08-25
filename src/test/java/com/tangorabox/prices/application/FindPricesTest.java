package com.tangorabox.prices.application;

import com.tangorabox.prices.domain.mappers.PriceMapperImpl;
import com.tangorabox.prices.domain.models.Price;
import com.tangorabox.prices.domain.models.PriceRepository;
import com.tangorabox.prices.domain.models.PriceRequest;
import com.tangorabox.prices.testutils.Fixtures;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class FindPricesTest {

    @Mock
    private PriceRepository priceRepository;
    private FindPrices findPrices;

    @BeforeEach
    void beforeEach() {
        findPrices = new FindPrices(priceRepository, new PriceMapperImpl());
    }

    @Test
    void multiplePricesShouldReturnTheMajorPriority() {
        //Given
        final List<Price> multiplePrices = multiplePricesSortedByPriority();
        given(priceRepository.find(any())).willReturn(Flux.fromIterable(multiplePrices));
        PriceRequest request = Fixtures.getObject(PriceRequest.class);

        //When/Then
        StepVerifier.create(findPrices.execute(request))
                .expectNextMatches(priceResponse -> priceResponse.getBrandId() == multiplePrices.get(multiplePrices.size() - 1).getBrandId())
                .verifyComplete();

    }

    private List<Price> multiplePricesSortedByPriority() {
        final List<Price> prices = Fixtures.getList(Price.class);
        for (int i = 0; i < prices.size(); i++) {
            prices.get(i).setPriority(i);
            prices.get(i).setBrandId(i);
        }
        return prices;
    }


    @Test
    void priceNotFounded() {
        //Given
        given(priceRepository.find(any())).willReturn(Flux.empty());
        PriceRequest request = Fixtures.getObject(PriceRequest.class);

        //When/Then
        StepVerifier.create(findPrices.execute(request))
                .verifyComplete();
    }
}
