package com.tangorabox.prices;

import com.tangorabox.prices.application.FindPrices;
import com.tangorabox.prices.domain.mappers.PriceMapper;
import com.tangorabox.prices.domain.models.PriceRepository;
import com.tangorabox.prices.infrastructure.mappers.PriceMapperInfra;
import com.tangorabox.prices.infrastructure.service.PricesDatabaseUpdater;
import io.r2dbc.h2.H2ConnectionConfiguration;
import io.r2dbc.h2.H2ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Component
@EnableR2dbcRepositories
@Slf4j
public class AppConfigTest extends AbstractR2dbcConfiguration {

    private static final Path CSV_PATH = Path.of("prices.csv");

    public AppConfigTest() {
        assert Files.exists(CSV_PATH) : "The test file must exists";
        assert Files.isRegularFile(CSV_PATH) : "The test file must be a valid file";
    }

    @Bean
    FindPrices findPricesBean(PriceRepository priceRepository) {
        return new FindPrices(priceRepository, Mappers.getMapper(PriceMapper.class));
    }

    @Bean
    public PriceMapperInfra priceMapper() {
        return Mappers.getMapper(PriceMapperInfra.class);
    }

    @Bean
    public H2ConnectionFactory connectionFactory() {
        return new H2ConnectionFactory(
                H2ConnectionConfiguration.builder()
                        .url("mem:testdb;DB_CLOSE_DELAY=-1;")
                        .username("sa")
                        .build()
        );
    }

    @Bean
    ApplicationRunner init(PricesDatabaseUpdater pricesDatabaseUpdater, DatabaseClient client) {
        return args -> {
            var initDb = client.sql("create table PRICES" +
                    "(id SERIAL PRIMARY KEY, " +
                    "brand_id long," +
                    "start_date TIMESTAMP, " +
                    "end_date TIMESTAMP, " +
                    "rate integer, " +
                    "product_id long, " +
                    "priority integer, " +
                    "sale_price DECIMAL(15, 2), " +
                    "currency varchar(255));");

            initDb.then().doAfterTerminate(() -> {
                try {
                    pricesDatabaseUpdater.update(CSV_PATH);
                } catch (IOException e) {
                    log.error("Database Error", e);
                }
            }).subscribe();
        };
    }
}
