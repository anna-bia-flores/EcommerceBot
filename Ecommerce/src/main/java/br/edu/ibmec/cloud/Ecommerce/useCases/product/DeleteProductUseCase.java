package br.edu.ibmec.cloud.Ecommerce.useCases.product;

import br.edu.ibmec.cloud.Ecommerce.repositories.cosmos.ProductCosmosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteProductUseCase {

    private final ProductCosmosRepository repository;

    public void execute(String id) {
        repository.deleteById(id);
    }
}
