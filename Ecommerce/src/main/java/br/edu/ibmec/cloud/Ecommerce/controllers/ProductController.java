package br.edu.ibmec.cloud.Ecommerce.controllers;

import br.edu.ibmec.cloud.Ecommerce.dtos.product.CreateProductDto;
import br.edu.ibmec.cloud.Ecommerce.dtos.product.ProductDto;
import br.edu.ibmec.cloud.Ecommerce.dtos.product.UpdateProductDto;
import br.edu.ibmec.cloud.Ecommerce.models.Product;
import br.edu.ibmec.cloud.Ecommerce.useCases.product.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final CreateProductUseCase createProductUseCase;
    private final ListProductsUseCase listProductsUseCase;
    private final GetProductByIdUseCase getProductByIdUseCase;
    private final SearchProductByNameUseCase searchProductByNameUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody CreateProductDto dto) {
        try {
            return ResponseEntity.ok(createProductUseCase.execute(dto));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAll() {
        return ResponseEntity.ok(listProductsUseCase.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getById(@PathVariable String id) {
        return getProductByIdUseCase.execute(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductDto>> searchByName(@RequestParam String name) {
        List<ProductDto> results = searchProductByNameUseCase.execute(name);
        return ResponseEntity.ok(results);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable String id, @RequestBody UpdateProductDto dto) {
        return updateProductUseCase.execute(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        deleteProductUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }
}
