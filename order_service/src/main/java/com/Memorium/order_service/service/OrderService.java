package com.Memorium.order_service.service;

import com.Memorium.order_service.entity.Order;
import com.Memorium.order_service.repository.OrderRepository;
import com.Memorium.order_service.service.inter.OrderServiceInt;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class OrderService implements OrderServiceInt {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order getOrderByOrderId(long orderId) {
        try {
            return orderRepository.findById(orderId)
                    .orElseThrow(() -> new EntityNotFoundException(
                            String.format("Order with OrderId %d not found", orderId)));
        } catch (EntityNotFoundException e) {
            log.error("Error fetching Order: {}", e.getMessage());
            return null;
        } catch (Exception e) {
            log.error("Unexpected error occurred while fetching Order: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch Order", e);
        }
    }

    @Override
    @Transactional
    public Order addOrder(Order order) {
        try {
            return orderRepository.save(order);
        } catch (DataIntegrityViolationException e) {
            log.error("Constraint violation while adding Order: {}", e.getMessage());
            throw new RuntimeException("Failed to add Order due to invalid data", e);
        } catch (Exception e) {
            log.error("Unexpected error occurred while adding Order: {}", e.getMessage());
            throw new RuntimeException("Failed to add Order", e);
        }
    }

    @Override
    @Transactional
    public Order updateOrder(long orderId, Order updatedOrder) {
        try {
            Order existingOrder = orderRepository.findById(orderId)
                    .orElseThrow(() -> new EntityNotFoundException(
                            String.format("Order with OrderId %d not found", orderId)));

            existingOrder.setEmail(updatedOrder.getEmail());
            existingOrder.setStatus(updatedOrder.getStatus());

            return orderRepository.save(existingOrder);
        } catch (EntityNotFoundException e) {
            log.error("Error updating Order: {}", e.getMessage());
            return null;
        } catch (DataIntegrityViolationException e) {
            log.error("Constraint violation while updating Order: {}", e.getMessage());
            throw new RuntimeException("Failed to update Order due to invalid data", e);
        } catch (Exception e) {
            log.error("Unexpected error occurred while updating Order: {}", e.getMessage());
            throw new RuntimeException("Failed to update Order", e);
        }
    }

    @Override
    @Transactional
    public void deleteOrder(long orderId) {
        try {
            Order existingOrder = orderRepository.findById(orderId)
                    .orElseThrow(() -> new EntityNotFoundException(
                            String.format("Order with OrderId %d not found", orderId)));

            orderRepository.delete(existingOrder);
        } catch (EntityNotFoundException e) {
            log.error("Error deleting Order: {}", e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error occurred while deleting Order: {}", e.getMessage());
            throw new RuntimeException("Failed to delete Order", e);
        }
    }

    @Override
    @Transactional
    public Order updateOrderStatus(long orderId, Order.OrderStatus orderStatus) {
        try {
            Order existingOrder = orderRepository.findById(orderId)
                    .orElseThrow(() -> new EntityNotFoundException(
                            String.format("Order with OrderId %d not found", orderId)));

            existingOrder.setStatus(orderStatus);
            return orderRepository.save(existingOrder);
        } catch (EntityNotFoundException e) {
            log.error("Error updating order status for Order: {}", e.getMessage());
            return null;
        }
    }
}
