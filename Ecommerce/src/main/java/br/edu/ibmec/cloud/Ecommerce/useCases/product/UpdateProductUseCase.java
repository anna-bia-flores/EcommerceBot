package br.edu.ibmec.cloud.Ecommerce.useCases.product;

import br.edu.ibmec.cloud.Ecommerce.dtos.product.UpdateProductDto;
import br.edu.ibmec.cloud.Ecommerce.models.Product;
import br.edu.ibmec.cloud.Ecommerce.repositories.cosmos.ProductCosmosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateProductUseCase {

    private final ProductCosmosRepository repository;

    public Optional<Product> execute(String id, UpdateProductDto dto) {
        return repository.findById(id).map(existing -> {
            dto.applyTo(existing);
            return repository.save(existing);
        });
    }
}
