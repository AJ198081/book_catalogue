package com.exercise.bookcatalogue.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    @KafkaListener(topics = "bookCatalogue", groupId = "books")
    public void listener(String data) {
        System.out.println("Kafka Listener has received: " + data);
    }

}
