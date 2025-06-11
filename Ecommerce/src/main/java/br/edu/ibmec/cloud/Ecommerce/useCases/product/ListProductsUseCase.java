package br.edu.ibmec.cloud.Ecommerce.useCases.product;

import br.edu.ibmec.cloud.Ecommerce.dtos.product.ProductDto;
import br.edu.ibmec.cloud.Ecommerce.repositories.cosmos.ProductCosmosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ListProductsUseCase {

    private final ProductCosmosRepository repository;

    public List<ProductDto> execute() {
        return StreamSupport.stream(repository.findAll().spliterator(), false)
                .map(ProductDto::fromEntity)
                .collect(Collectors.toList());
    }
}
