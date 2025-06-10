package br.edu.ibmec.cloud.Ecommerce.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "credit_cards")
@Data
public class CreditCard {

    @Id
    private String id;

    private String cardNumber;

    @Column(nullable = false, length = 4)
    private String cvv;


    @Column(name = "expiration_date") // formato MM/yyyy
    private String expirationDate;

    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /** Business Logic **/

    public boolean isValid() {
        try {
            YearMonth exp = YearMonth.parse(expirationDate, DateTimeFormatter.ofPattern("MM/yyyy"));
            return exp.isAfter(YearMonth.now());
        } catch (Exception e) {
            return false;
        }
    }

    public boolean hasSufficientBalance(BigDecimal amount) {
        return balance != null && balance.compareTo(amount) >= 0;
    }

    public void debit(BigDecimal amount) {
        if (!hasSufficientBalance(amount)) {
            throw new IllegalArgumentException("Insufficient credit card balance");
        }
        balance = balance.subtract(amount);
    }

    public void credit(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Invalid credit amount");
        }
        balance = balance.add(amount);
    }
}
