package br.edu.ibmec.cloud.Ecommerce.useCases.product;

import br.edu.ibmec.cloud.Ecommerce.dtos.product.ProductDto;
import br.edu.ibmec.cloud.Ecommerce.repositories.cosmos.ProductCosmosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchProductByNameUseCase {

    private final ProductCosmosRepository repository;

    public List<ProductDto> execute(String name) {
        return repository.findByNameContaining(name)
                .stream()
                .map(ProductDto::fromEntity)
                .collect(Collectors.toList());
    }
}
