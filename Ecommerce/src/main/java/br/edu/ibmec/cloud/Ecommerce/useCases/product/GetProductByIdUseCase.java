package br.edu.ibmec.cloud.Ecommerce.useCases.product;

import br.edu.ibmec.cloud.Ecommerce.dtos.product.ProductDto;
import br.edu.ibmec.cloud.Ecommerce.repositories.cosmos.ProductCosmosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetProductByIdUseCase {

    private final ProductCosmosRepository repository;

    public Optional<ProductDto> execute(String id) {
        return repository.findById(id)
                .map(ProductDto::fromEntity);
    }
}
