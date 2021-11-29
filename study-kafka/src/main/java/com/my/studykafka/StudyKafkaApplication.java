package com.my.studykafka;

import com.my.studykafka.config.KafkaConsumerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@Slf4j
public class StudyKafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudyKafkaApplication.class, args);
    }

    @KafkaListener(topics = KafkaConsumerConfig.TOPIC_NAME, groupId = KafkaConsumerConfig.GROUP_ID)
    public void listenGroupFoo(String message) {
        log.info("Received Message : {}", message);
    }

}
