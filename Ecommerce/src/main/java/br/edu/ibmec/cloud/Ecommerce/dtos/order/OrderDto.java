package br.edu.ibmec.cloud.Ecommerce.dtos.order;

import br.edu.ibmec.cloud.Ecommerce.models.Order;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Data
public class OrderDto {

    private String id;
    private LocalDateTime createdAt;
    private String status;

    private String userId;
    private String userEmail;

    private BigDecimal totalAmount;

    private List<OrderItemDto> items;

    public static OrderDto fromEntity(Order order) {
        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setCreatedAt(order.getCreatedAt());
        dto.setStatus(order.getStatus().name());
        dto.setUserId(order.getUserId());
        dto.setUserEmail(order.getUserEmail());
        dto.setTotalAmount(Optional.ofNullable(order.getTotalAmount()).orElse(BigDecimal.ZERO));
        dto.setItems(order.getItems().stream().map(OrderItemDto::fromEntity).toList());
        return dto;
    }
}
