package com.tangorabox.prices.infrastructure.io;

import com.google.common.collect.Lists;
import com.tangorabox.prices.infrastructure.entity.PriceEntity;
import com.tangorabox.prices.infrastructure.mappers.PriceMapperInfra;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.tangorabox.prices.testutils.AssertionUtils.assertValues;

public class PriceMapperTest extends CSVFileTest {

    @Test
    public void testMapper() throws IOException {
        //Given
        PriceMapperInfra mapper = Mappers.getMapper(PriceMapperInfra.class);
        CSVReaderOpenCSV csvReaderOpenCSV = new CSVReaderOpenCSV();
        Iterable<PriceCSV> pricesCSV = csvReaderOpenCSV.readFileAsIterable(CSV_PATH);

        //When
        List<PriceEntity> pricesConverted = Lists.newArrayList(pricesCSV)
                .stream().map(mapper::toPriceEntity
                ).collect(Collectors.toList());

        //Then
        assertValues(pricesConverted);
    }

}
