package com.Memorium.order_service.service;

import com.Memorium.order_service.entity.OrderItem;
import com.Memorium.order_service.entity.OrderItemId;
import com.Memorium.order_service.repository.OrderItemRepository;
import com.Memorium.order_service.repository.OrderRepository;
import com.Memorium.order_service.service.inter.OrderItemServiceInt;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderItemService implements OrderItemServiceInt {
    private static final Logger log = LoggerFactory.getLogger(OrderItemService.class);
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public OrderItem getOrderItemByOrderItemId(long orderId, long itemId) {
        try {
            OrderItem orderItem = orderItemRepository.findByOrderItemId_OrderIdAndOrderItemId_ItemId(orderId, itemId);
            if (orderItem == null) {
                throw new EntityNotFoundException(
                        String.format("OrderItem with OrderId %d and ItemId %d not found", orderId, itemId)
                );
            }
            return orderItem;
        } catch (EntityNotFoundException e) {
            log.error("Error fetching OrderItem: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred while fetching OrderItem: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch OrderItem", e);
        }
    }

    @Override
    public List<OrderItem> getOrderItemByOrderId(long orderId) {
        try {
            List<OrderItem> orderItems = orderItemRepository.findAllByOrderItemId_OrderId(orderId);
            if (orderItems.isEmpty()) {
                log.warn("No OrderItems found for OrderId {}", orderId);
            }
            return orderItems;
        } catch (Exception e) {
            log.error("Unexpected error occurred while fetching OrderItems: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch OrderItems", e);
        }
    }


    @Override
    @Transactional
    public OrderItem addOrderItem(OrderItem orderItem) {
        try {
            return orderItemRepository.save(orderItem);
        } catch (DataIntegrityViolationException e) {
            log.error("Constraint violation while adding OrderItem: {}", e.getMessage());
            throw new RuntimeException("Failed to add OrderItem due to invalid data", e);
        } catch (Exception e) {
            log.error("Unexpected error occurred while adding OrderItem: {}", e.getMessage());
            throw new RuntimeException("Failed to add OrderItem", e);
        }
    }

    // this is only used to update quantity
    @Override
    @Transactional
    public OrderItem updateOrderItem(long orderId, long itemId, OrderItem newOrderItem) {
        try {
            OrderItem foundOrderItem = orderItemRepository.findByOrderItemId_OrderIdAndOrderItemId_ItemId(orderId, itemId);

            if (foundOrderItem == null) {
                throw new EntityNotFoundException(String.format(
                        "OrderItem with OrderId %d and ItemId %d nto found", orderId, itemId
                ));
            }

            foundOrderItem.setQuantity(newOrderItem.getQuantity());
            return orderItemRepository.save(foundOrderItem);
        } catch (EntityNotFoundException e) {
            log.error("Error updating OrderItem: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Unexpected error occurred while updating OrderItem: {}", e.getMessage());
            throw new RuntimeException("Failed to update OrderItem ", e);
        }
    }

    @Override
    @Transactional
    public void deleteOrderItem(long orderId, long itemId) {
        try {
            OrderItem orderItem = orderItemRepository.findByOrderItemId_OrderIdAndOrderItemId_ItemId(orderId, itemId);

            if (orderItem == null) {
                throw new EntityNotFoundException(
                        String.format("OrderItem with OrderId %d and ItemId %d not found", orderId, itemId)
                );
            }

            // Delete the found OrderItem
            orderItemRepository.delete(orderItem);
        } catch (EntityNotFoundException e) {
            log.error("Error deleting OrderItem: {}", e.getMessage());
            throw e;

        } catch (Exception e) {
            log.error("Unexpected error occurred while deleting OrderItem: {}", e.getMessage());
            throw new RuntimeException("Failed to delete OrderItem", e);
        }
    }

    @Override
    public boolean isValidOrderItem(OrderItem orderItem) {
        OrderItemId orderItemId = orderItem.getOrderItemId();
        long orderId = orderItemId.getOrderId();
        long itemId = orderItemId.getItemId();

        if (!orderRepository.existsById(orderId)) return false;

        //TODO - need to check if itemId exists in product-service
        // for now, we will assume itemId exists for testing

        return true;
    }
}
