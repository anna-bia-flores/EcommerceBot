package br.edu.ibmec.cloud.Ecommerce.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Data
public class OrderItem {

    @Id
    private String id;

    private String productName;

    private int quantity;

    private BigDecimal unitPrice;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    /** Business Logic **/

    public BigDecimal getSubtotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}
