package com.Memorium.order_producer;

import com.Memorium.order_producer.payload.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/order")
@RestController
public class ProducerController {
    @Autowired
    private OrderProducer orderProducer;

    @PostMapping("/publish")
    public ResponseEntity<String> publish(@RequestBody Order order) {
        orderProducer.sendOrder(order);
        return ResponseEntity.ok(String.format("Order published, %s", order));
    }
}
