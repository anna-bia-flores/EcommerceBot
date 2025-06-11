package br.edu.ibmec.cloud.Ecommerce.useCases.product;

import br.edu.ibmec.cloud.Ecommerce.dtos.product.CreateProductDto;
import br.edu.ibmec.cloud.Ecommerce.models.Product;
import br.edu.ibmec.cloud.Ecommerce.repositories.cosmos.ProductCosmosRepository;
import com.azure.spring.data.cosmos.core.CosmosTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class CreateProductUseCase {
    private final ProductCosmosRepository repository;

    public Product execute(CreateProductDto dto) {
        Product product = dto.toEntity();
        return repository.save(product);
    }
}