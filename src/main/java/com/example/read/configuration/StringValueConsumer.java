package com.example.read.configuration;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Slf4j
@AllArgsConstructor
public class StringValueConsumer {

    private KafkaConsumerConfiguration configuration;

    private final Duration timeout = Duration.ofMillis(2_000);

    private final Consumer<String> dataConsumer;

    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    private Long count;

    public List<String> startSending() throws ExecutionException, InterruptedException {
        return executor.schedule(this::poll, 0, TimeUnit.MILLISECONDS).get();
    }

    private List<String> poll() {
        ConsumerRecords<String, String> records = configuration.getConsumer().poll(timeout);
        if(records.count() > 0)
        log.info("polled records.counter:{}", records.count());
        List<String> list = new ArrayList<>();
        Long i = 0L;
        for (ConsumerRecord<String, String> kafkaRecord : records) {
            if (i.equals(count)) {
                break;
            }
            try {
                var value = kafkaRecord.value();
                dataConsumer.accept(value);
                list.add(value);
            } catch (Exception ex) {
                log.error("can't parse record:{}", kafkaRecord, ex);
            }
            i = i + 1;
        }
        return list;
    }
}
