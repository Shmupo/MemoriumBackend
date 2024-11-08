package com.Memorium.order_consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.Memorium.order_consumer.payload.Order;

@Slf4j
@Component
public class OrderConsumer {
    @KafkaListener(topics = "order", groupId = "order-consumer-group")
    public void orderConsumer(Order order) {
        log.info("Consumer consume Kafka message -> {}", order);
    }
}
