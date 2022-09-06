package com.example.configuration;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
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

    public void startSending() {
        //executor.scheduleAtFixedRate(this::poll, 0, MAX_POLL_INTERVAL_MS, TimeUnit.MILLISECONDS);
        executor.schedule(this::poll, 0, TimeUnit.MILLISECONDS);
    }

    private void poll() {
        ConsumerRecords<String, String> records = configuration.getConsumer().poll(timeout);
        if(records.count() > 0)
        log.info("polled records.counter:{}", records.count());
        for (ConsumerRecord<String, String> kafkaRecord : records) {
            try {
                var key = kafkaRecord.key();
                var value = kafkaRecord.value();
                log.info("key:{}, value:{}, record:{}", key, value, kafkaRecord);
                dataConsumer.accept(value);
            } catch (Exception ex) {
                log.error("can't parse record:{}", kafkaRecord, ex);
            }
        }
    }
}
