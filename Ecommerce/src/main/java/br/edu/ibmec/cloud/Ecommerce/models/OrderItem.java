package br.edu.ibmec.cloud.Ecommerce.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Optional;

@Data
@NoArgsConstructor
public class OrderItem {

    private String productId;
    private String name;
    private int quantity;
    private String subtotal = "0.00";
    private String unitPrice = "0.00";

    // ✅ Interface pública para definir preço (e atualizar subtotal automaticamente)
    public void setUnitPriceDecimal(BigDecimal value) {
        this.unitPrice = value != null ? value.toPlainString() : "0.00";
        calculateSubtotal();
    }

    // ✅ Interface pública para expor subtotal como BigDecimal para leitura
    public BigDecimal getSubtotalDecimal() {
        return new BigDecimal(Optional.ofNullable(subtotal).orElse("0.00"));
    }

    // ✅ Interface pública para expor unitPrice como BigDecimal para leitura (usado em DTOs)
    public BigDecimal getUnitPriceDecimal() {
        return new BigDecimal(Optional.ofNullable(unitPrice).orElse("0.00"));
    }

    // 🔒 Métodos privados (lógica interna)
    private void setSubtotalDecimal(BigDecimal value) {
        this.subtotal = value != null ? value.toPlainString() : "0.00";
    }

    private void calculateSubtotal() {
        BigDecimal subtotalCalc = getUnitPriceDecimal().multiply(BigDecimal.valueOf(quantity));
        setSubtotalDecimal(subtotalCalc);
    }
}
