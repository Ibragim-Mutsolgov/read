package com.example.read.configuration;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.yaml.snakeyaml.reader.StreamReader;

import java.io.*;
import java.net.InetAddress;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Configuration
public class KafkaConsumerConfiguration {

    private String bootstrapServers = "";

    private KafkaConsumer<String, String> kafkaConsumer;

    public KafkaConsumerConfiguration() throws FileNotFoundException {
        File file = new File("properties/kafka.properties");
        try(FileReader reader = new FileReader(file))
        {
            int c;
            while((c=reader.read())!=-1){
                bootstrapServers = bootstrapServers + (char)c;
            }
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    @Bean
    public Map<String, Object> consumerConfig() {
        Map<String, Object> props = new HashMap();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers.split("=")[1]);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "groupId");
        props.put(ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, makeGroupInstanceIdConfig());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        kafkaConsumer = new KafkaConsumer<>(props);
        kafkaConsumer.subscribe(Collections.singletonList("Data_Delivery"));

        return props;
    }

    public KafkaConsumer<String, String> getConsumer() {
        return kafkaConsumer;
    }

    public String makeGroupInstanceIdConfig()  {
        try {
            var hostName = InetAddress.getLocalHost().getHostName();
            return String.join("-", "groupId", hostName, String.valueOf(new Random().nextInt(100_999_999)));
        } catch(Exception ex) {
            return ex.getMessage();
        }
    }
}
