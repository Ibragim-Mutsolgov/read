package com.example.controller;

import com.example.configuration.KafkaConsumerConfiguration;
import com.example.model.Data;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;
import java.util.stream.StreamSupport;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/read/data")
public class ReadController {

    private KafkaTemplate<String, String> kafkaTemplate;

    private KafkaConsumerConfiguration configuration;

    @GetMapping("/{count}")
    public Data getData(@PathVariable Long count, HttpServletRequest request) {
        log.info("IP: " + request.getRemoteAddr());

        return new Data(
                1,
                2,
                3,
                new ArrayList<>()
        );
    }
}
