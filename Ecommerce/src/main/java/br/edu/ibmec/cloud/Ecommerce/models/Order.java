package br.edu.ibmec.cloud.Ecommerce.models;

import com.azure.spring.data.cosmos.core.mapping.Container;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Data
@NoArgsConstructor
@Container(containerName = "orders")
public class Order {

    public enum Status {
        PENDING, CONFIRMED, REJECTED
    }

    @Id
    private String id = UUID.randomUUID().toString();

    private LocalDateTime createdAt = LocalDateTime.now();
    private Status status = Status.PENDING;

    private String userId;
    private String userEmail;

    private String creditCardId;

    private List<OrderItem> items;


    public BigDecimal getTotalAmount() {
        if (items == null || items.isEmpty()) {
            System.out.println("⚠️ Pedido sem itens!");
            return BigDecimal.ZERO;
        }
        return items.stream()
                .map(OrderItem::getSubtotalDecimal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void placeOrder(CreditCard card, String cvv) {
        if (items == null || items.isEmpty()) {
            throw new IllegalStateException("Order must have at least one item");
        }

        BigDecimal total = getTotalAmount();
        System.out.println("🔍 Total do pedido: " + total);

        if (!card.isValid()) {
            this.status = Status.REJECTED;
            System.out.println("❌ Cartão expirado: " + card.getExpirationDate());
            throw new IllegalStateException("Credit card is expired or invalid");
        }

        if (!card.isValid(cvv)) {
            this.status = Status.REJECTED;
            System.out.println("❌ CVV inválido");
            throw new IllegalArgumentException("Invalid CVV for credit card");
        }

        if (!card.hasSufficientBalance(total)) {
            this.status = Status.REJECTED;
            System.out.println("❌ Saldo insuficiente: " + card.getBalance());
            throw new IllegalStateException("Insufficient balance on credit card");
        }

        card.debit(total);

        // Preenche os dados do cartão para armazenar no pedido
        setCreditCardId(card.getId());

        this.status = Status.CONFIRMED;
        System.out.println("✅ Pedido confirmado. Novo saldo: " + card.getBalance());
    }
}
