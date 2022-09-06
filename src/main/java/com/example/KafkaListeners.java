package com.example;

import com.example.model.Data;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    @KafkaListener(topics = "Data_Delivery", groupId = "groupId")
    void listeners(String string) {
        System.out.println(string);
    }
}
