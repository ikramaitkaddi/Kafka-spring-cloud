package com.vinsguru.processor;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Arrays;
import java.util.function.Function;
import java.util.regex.Pattern;

import static org.apache.kafka.streams.kstream.Suppressed.BufferConfig.unbounded;
import static org.apache.kafka.streams.kstream.Suppressed.untilWindowCloses;

@Configuration
public class KafkaProcessor {

    /*
     *   process the numbers received via kafka topic
     *   Function<T, R> makes this as kafka stream processor
     *   T is input type
     *   R is output type
     *
     * */

    @Bean
    public Function<KStream<String, String>, KStream<String, String>> evenNumberSquareProcessor() {
        //  Pattern p = Pattern.compile("-?\\d+");

        return kStream ->
                kStream.
                        filter((k, v) -> v.charAt(0) != '%' && v.charAt(0) != '@')
                        .peek((k, v) -> System.out.println(" Event : " + v));


        //*.map((k,v)->new KeyValue<>(""+v,0L))
        // .groupBy((k,v)->k, Grouped.with(Serdes.String(),Serdes.String()))
                      /*  .groupByKey()
                        .windowedBy(TimeWindows.of(Duration.ofSeconds(10)))
                        .aggregate(()->0L, ((key, value, aggregate) -> {
                            aggregate = aggregate + value;
                            return aggregate;
                        }),Materialized.with(Serdes.String(), Serdes.Long()))
                        //.mapValues(value -> value +2)
                        .suppress(untilWindowCloses(unbounded()))
                         .toStream()

                        //.peek((k, v) -> System.out.println("Squaring Even : " + v));
                        .map((k,v)->new KeyValue<>("ikram =>",v))
                        .peek((k, v) -> System.out.println("Squaring Even : " + v));




















                         //.peek((k, v) -> System.out.println("Squaring Even : " + v))
                                 /*.map((k,v)->new KeyValue<>(v,0L))
                                 .groupBy((k,v)->k, Grouped.with(Serdes.String(),Serdes.Long()))
                                 .windowedBy(TimeWindows.of(Duration.ofSeconds(5)))
                                 .count()
                                 .toStream()
                                 .map((k,v)->new KeyValue<>("=>"+k.window().startTime()+k.window().endTime()+":"+k.key(),v));
                                 .map(s2 -> s2.charAt(0))*/
    };

   /* @Bean
    public Function<KStream<String,String>, KStream<String,String>> evenNumberSquareProcessor(){
        System.out.println("**********************");
        return (input)->{
            return input
                    .map((k,v)->new KeyValue<>(v,0L))
                    .groupBy((k,v)->k,Grouped.with(Serdes.String(),Serdes.Long()))
                    .windowedBy(TimeWindows.of(Duration.ofSeconds(1)))
                    .count(Materialized.as("count"))
                    .toStream()
                    .map((k,v)->new KeyValue<>("=>"+k.window().startTime()+k.window().endTime()+":"+k.key(),v.toString()));
                   // .peek((k, v) -> System.out.println("Squaring Even : " + v));


        };
    }*/
}
