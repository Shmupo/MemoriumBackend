package com.Memorium.order_service.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

// DTO containing the minimum data points needed to create a new order
// also includes the order items associated with this order

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderListDTO {
    @NotNull
    @Size(max = 144, min = 6, message = "Email must be between 6 and 144 chars")
    private String email;

    @NotNull
    private List<OrderItem> itemOrders;
}
