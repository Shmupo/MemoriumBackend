package com.Memorium.order_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class OrderItemId implements Serializable {
    @Column(nullable = false)
    private long orderId;

    @Column(nullable = false)
    private long itemId;

    // may need to add explicit equals() and hashCode()
}
