package br.edu.ibmec.cloud.Ecommerce.dtos.product;

import br.edu.ibmec.cloud.Ecommerce.models.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CreateProductDto {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank(message = "Category is required")
    private String category;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price must be non-negative")
    private BigDecimal price;

    @Min(value = 0, message = "Stock must be at least 0")
    private int stock;

    public Product toEntity() {
        Product product = new Product();
        product.setId(UUID.randomUUID().toString());
        product.setName(this.name);
        product.setDescription(this.description);
        product.setCategory(this.category);
        product.setPriceDecimal(this.price);
        product.setStock(this.stock);
        return product;
    }
}
