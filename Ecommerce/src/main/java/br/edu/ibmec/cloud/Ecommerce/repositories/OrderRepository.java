package br.edu.ibmec.cloud.Ecommerce.repositories;

import br.edu.ibmec.cloud.Ecommerce.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {
}
