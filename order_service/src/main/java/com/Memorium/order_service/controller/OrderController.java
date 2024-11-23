package com.Memorium.order_service.controller;

import com.Memorium.order_service.entity.Order;
import com.Memorium.order_service.entity.OrderListDTO;
import com.Memorium.order_service.service.OrderListDTOService;
import com.Memorium.order_service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/order-service/")
@RestController
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderListDTOService orderListDTOService;

    @GetMapping("order/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable("orderId") long orderId) {
        Order foundOrder = orderService.getOrderByOrderId(orderId);

        if (foundOrder != null) {
            return new ResponseEntity<Order>(foundOrder, HttpStatus.OK);
        } else {
            return new ResponseEntity<Order>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("place")
    public HttpStatus createOrder(@RequestBody OrderListDTO orderListDTO) {

        if (!orderListDTOService.isValidOrderListDTO(orderListDTO)) {
            System.out.println("ERROR : INVALID OrderListDTO related to " + orderListDTO.getEmail());
            System.out.println("WITH ITEMS LIST OF LENGTH " + orderListDTO.getItemOrders().size());
            return HttpStatus.BAD_REQUEST;
        }

        // create and store new order record
        Order newOrder = new Order(orderListDTO.getEmail());
        orderService.addOrder(newOrder);

        return HttpStatus.OK;
    }

    @PostMapping("/cancel/{orderId}")
    public HttpStatus cancelOrder(@PathVariable("orderId") long orderId) {
        if (orderService.updateOrderStatus(orderId, Order.OrderStatus.CANCELLED) == null) {
            System.out.println("ERROR : COULD NOT CANCEL ORDER WITH orderId: " + orderId);
            return HttpStatus.BAD_REQUEST;
        }

        return HttpStatus.OK;
    }
}
