package com.Memorium.order_consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.Memorium.order_consumer.payload.Order;

@Slf4j
@Component
public class OrderConsumer {
    // TODO - look into WebClient to send non-blocking HTTP requests to the order-service

    @KafkaListener(topics = "order", groupId = "order-consumer-group")
    public void orderConsumer(Order order) {
        log.info("Consumer consume Kafka message -> {}", order);
    }
}
