package br.edu.ibmec.cloud.Ecommerce.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
public class Order {

    public enum Status {
        PENDING, CONFIRMED, REJECTED
    }

    @Id
    private String id;

    private LocalDateTime createdAt = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private Status status = Status.PENDING;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "credit_card_id", nullable = false)
    private CreditCard creditCard;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;

    public BigDecimal getTotalAmount() {
        return items.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void placeOrder() {
        BigDecimal total = getTotalAmount();

        if (!creditCard.isValid()) {
            this.status = Status.REJECTED;
            throw new IllegalStateException("Credit card is expired or invalid");
        }

        if (!creditCard.hasSufficientBalance(total)) {
            this.status = Status.REJECTED;
            throw new IllegalStateException("Insufficient balance on credit card");
        }

        creditCard.debit(total);
        this.status = Status.CONFIRMED;
    }
}
