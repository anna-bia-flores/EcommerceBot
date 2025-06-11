package br.edu.ibmec.cloud.Ecommerce.repositories.cosmos;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import br.edu.ibmec.cloud.Ecommerce.models.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderCosmosRepository extends CosmosRepository<Order, String> {
}
