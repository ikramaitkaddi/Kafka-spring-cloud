package com.vinsguru.producer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.function.Supplier;
import java.util.stream.Stream;
@Configuration
public class KafkaProducer {
    private static String CsvFile = "diabetes.txt";
    /*
    *   produce a number from 1, every second
    *   Supplier<T> makes this as kafka producer of T
    * */

    @Bean
    public Supplier<Flux<String>> numberProducer() throws URISyntaxException, IOException {
        /*return () -> Flux.range(1, 1000)
                        .map(i -> (long) i)
                        .delayElements(Duration.ofSeconds(1));*/
        URI uri = getClass().getClassLoader().getResource(CsvFile).toURI();
        Stream<String> FileStream = Files.lines(Paths.get(uri));
        return () -> Flux.fromStream(FileStream)
                .map(line -> (String) line)
                .delayElements(Duration.ofSeconds(1));

    };

}
