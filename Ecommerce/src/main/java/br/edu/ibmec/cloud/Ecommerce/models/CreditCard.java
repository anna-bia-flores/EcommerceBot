package br.edu.ibmec.cloud.Ecommerce.models;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
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
            if (expirationDate == null || !expirationDate.matches("^\\d{2}/\\d{2}$")) {
                System.out.println("⚠️ Data nula ou formato inválido: " + expirationDate);
                return false;
            }

            // Pega data atual no formato MM/yy
            int currentMonth = LocalDateTime.now().getMonthValue();
            int currentYear = LocalDateTime.now().getYear() % 100; // só os dois últimos dígitos

            String[] parts = expirationDate.split("/");
            int expMonth = Integer.parseInt(parts[0]);
            int expYear = Integer.parseInt(parts[1]);

            System.out.printf("📅 Hoje: %02d/%02d | Cartão: %02d/%02d%n",
                    currentMonth, currentYear, expMonth, expYear);

            // Comparação simples: ano maior ou mesmo ano e mês maior ou igual
            return expYear > currentYear || (expYear == currentYear && expMonth >= currentMonth);
        } catch (Exception e) {
            System.out.println("⚠️ Erro ao validar cartão: " + e.getMessage());
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

    public boolean isValid(String cvv) {
        return this.cvv != null && this.cvv.equals(cvv);
    }
}
