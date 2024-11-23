package com.Memorium.order_service.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "OrderItems"
)
public class OrderItem {
    @EmbeddedId
    private OrderItemId orderItemId;

    @NotNull
    private int quantity;
}
