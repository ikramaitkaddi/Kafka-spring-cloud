package com.vinsguru.consumer;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.function.Consumer;

@Configuration
public class KafkaConsumer {

    /*
    *   consume the numbers received via kafka topic
    *   Consumer<T> makes this as kafka consumer of T
    * */
    public List<Long>  ages =  new ArrayList<Long>();
    public List<Double>  masses =  new ArrayList<Double>();
    public double avgAge =0.0;
    public double avgMass= 0.0;
    @Bean
    public Consumer<KStream<String,String>> squaredNumberConsumer(){
        return stream -> stream.foreach((key, value) -> {

                    StringTokenizer st = new StringTokenizer(value,",");
           // values.add(Long.valueOf(value));
            //values.forEach(val->System.out.println("helo in  consumer" +val));
            StringBuilder sb = new StringBuilder();
            while(st.hasMoreTokens()) {
                int preg = Integer.parseInt(st.nextToken());
                int plas = Integer.parseInt(st.nextToken());
                int pres = Integer.parseInt(st.nextToken());
                int skin = Integer.parseInt(st.nextToken());
                int insu = Integer.parseInt(st.nextToken());
                double mass = Double.parseDouble(st.nextToken());
                double pedi = Double.parseDouble(st.nextToken());
                int age = Integer.parseInt(st.nextToken());
                String classe = st.nextToken();
                ages.add((long) age);
                masses.add(mass);
                ages.forEach(ag -> avgAge= avgAge +ag);
                masses.forEach(ma-> avgMass = avgMass +ma);
                avgAge = avgAge/ages.size();
                avgMass = avgMass/masses.size();
                System.out.println("Average of age : " + avgAge +"   Average of mass : " + avgMass  );
            }

            }
        );
    };

}
