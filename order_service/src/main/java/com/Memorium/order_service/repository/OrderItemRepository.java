package com.Memorium.order_service.repository;

import com.Memorium.order_service.entity.OrderItem;
import com.Memorium.order_service.entity.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    OrderItem findByOrderItemId_OrderIdAndOrderItemId_ItemId(long orderId, long itemId);
    List<OrderItem> findAllByOrderItemId_OrderId(long orderId);
}
