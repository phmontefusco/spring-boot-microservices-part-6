package com.programming.techie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@Slf4j
public class NotificationServiceApplication {


    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }


//    @KafkaListener(topics="notificationTopic")
    @KafkaListener(topics="${application.topic}")
    public void handleNotification(OrderPlacedEvent orderPlacedEvent){
        // send out a email notification

        log.info("Received notification for orderPlaced - {}", orderPlacedEvent.getOrderNumber());

    }


    @KafkaListener(topics="notificationTopic2")
    public void handleNotification2(String orderPlacedEvent){
        // send out a email notification

        log.info("Received notification for orderPlaced - {}", orderPlacedEvent);

    }



}
