package br.edu.ibmec.cloud.Ecommerce.dtos.order;

import br.edu.ibmec.cloud.Ecommerce.models.OrderItem;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {

    private String productId;
    private int quantity;


    // ✅ Converte DTO em entidade
    public OrderItem toEntity() {
        OrderItem item = new OrderItem();
        item.setProductId(productId);
        item.setQuantity(quantity);
        return item;
    }

    // ✅ Converte entidade em DTO (inclusive subtotal)
    public static OrderItemDto fromEntity(OrderItem item) {
        OrderItemDto dto = new OrderItemDto();
        dto.setProductId(item.getProductId());
        dto.setQuantity(item.getQuantity());
        return dto;
    }
}
