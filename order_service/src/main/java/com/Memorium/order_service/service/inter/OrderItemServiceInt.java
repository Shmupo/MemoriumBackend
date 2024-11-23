package com.Memorium.order_service.service.inter;

import com.Memorium.order_service.entity.OrderItem;

import java.util.List;

public interface OrderItemServiceInt {
    OrderItem getOrderItemByOrderItemId(long orderId, long itemId);
    List<OrderItem> getOrderItemByOrderId(long orderId);
    OrderItem addOrderItem(OrderItem orderItem);
    OrderItem updateOrderItem(long orderId, long itemId, OrderItem orderItem);
    void deleteOrderItem(long orderId, long itemId);
    boolean isValidOrderItem(OrderItem orderItem);
}
