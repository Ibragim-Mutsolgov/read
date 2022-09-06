package com.example.read.controller;

import com.example.read.configuration.KafkaConsumerConfiguration;
import com.example.read.configuration.StringValueConsumer;
import com.example.read.model.Data;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/read/data")
public class ReadController {

    private KafkaConsumerConfiguration configuration;

    @GetMapping("/{count}")
    public Data getData(@PathVariable Long count, HttpServletRequest request) throws ExecutionException, InterruptedException {
        log.info("IP: " + request.getRemoteAddr());
        var dataConsumer = new StringValueConsumer(configuration, value -> {}, count);
        List<String> list = dataConsumer.startSending();

        return new Data(
                1,
                2,
                3,
                list
        );
    }
}
