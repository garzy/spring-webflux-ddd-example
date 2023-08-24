package com.tangorabox.prices.application;

import com.tangorabox.prices.domain.entity.Price;
import com.tangorabox.prices.domain.entity.PriceRepository;
import com.tangorabox.prices.domain.entity.PriceRequest;
import com.tangorabox.prices.domain.entity.PriceResponse;
import com.tangorabox.prices.domain.mappers.PriceMapperImpl;
import com.tangorabox.prices.testutils.Fixtures;
import com.tangorabox.prices.testutils.PriceRequestMother;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
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
        given(priceRepository.find(any())).willReturn(multiplePrices);
        PriceRequest request = PriceRequestMother.test1();

        //When
        final Optional<PriceResponse> response = findPrices.execute(request);

        //Then
        then(response).isPresent();
        then(response.get().getBrandId()).isEqualTo(multiplePrices.get(multiplePrices.size() - 1).getBrandId());
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
        given(priceRepository.find(any())).willReturn(Collections.emptyList());
        PriceRequest request = PriceRequestMother.test1();

        //When
        final Optional<PriceResponse> response = findPrices.execute(request);

        //Then
        then(response).isEmpty();
    }
}
