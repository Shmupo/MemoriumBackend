package com.Memorium.order_service.service.inter;

import com.Memorium.order_service.entity.Order;

public interface OrderServiceInt {
    Order getOrderByOrderId(long orderId);
    Order addOrder(Order order);
    Order updateOrder(long orderId, Order order);
    void deleteOrder(long orderId);
    Order updateOrderStatus(long orderId, Order.OrderStatus orderStatus);
}
