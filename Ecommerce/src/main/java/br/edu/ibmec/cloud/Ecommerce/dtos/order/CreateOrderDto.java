package br.edu.ibmec.cloud.Ecommerce.dtos.order;

import br.edu.ibmec.cloud.Ecommerce.models.Order;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
public class CreateOrderDto {

    private String userId;
    private String userEmail;

    private String cvv;
    private String creditCardId;
    private List<OrderItemDto> items;

    public Order toEntity() {
        Order order = new Order();
        order.setUserId(userId);
        order.setCreditCardId(creditCardId);
        order.setUserEmail(userEmail);

        order.setItems(items.stream().map(OrderItemDto::toEntity).toList());
        return order;
    }
}
