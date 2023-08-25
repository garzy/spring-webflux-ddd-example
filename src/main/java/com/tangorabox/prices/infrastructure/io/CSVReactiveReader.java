package com.tangorabox.prices.infrastructure.io;

import reactor.core.publisher.Flux;

import java.io.IOException;
import java.nio.file.Path;

public interface CSVReactiveReader {

    Flux<PriceCSV> readFile(Path pathToFile) throws IOException;
}
