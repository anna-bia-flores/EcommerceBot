package br.edu.ibmec.cloud.Ecommerce.repositories.cosmos;

import br.edu.ibmec.cloud.Ecommerce.models.Product;
import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.azure.spring.data.cosmos.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductCosmosRepository extends CosmosRepository<Product, String> {

    @Query("SELECT * FROM p WHERE CONTAINS(LOWER(p.name), LOWER(@name))")
    List<Product> findByNameContaining(String name);
}