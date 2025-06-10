package br.edu.ibmec.cloud.Ecommerce.repositories;

import br.edu.ibmec.cloud.Ecommerce.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, String> {
}
