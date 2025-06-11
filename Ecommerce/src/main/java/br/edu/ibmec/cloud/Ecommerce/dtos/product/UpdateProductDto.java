package br.edu.ibmec.cloud.Ecommerce.dtos.product;

import br.edu.ibmec.cloud.Ecommerce.models.Product;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateProductDto {

    private String name;
    private String description;
    private String category;
    private BigDecimal price;
    private Integer stock; // <- Usar Integer para poder validar null

    public void applyTo(Product product) {
        if (this.name != null && !this.name.isBlank()) {
            product.setName(this.name);
        }

        if (this.description != null && !this.description.isBlank()) {
            product.setDescription(this.description);
        }

        if (this.category != null && !this.category.isBlank()) {
            product.setCategory(this.category);
        }

        if (this.price != null && this.price.compareTo(BigDecimal.ZERO) >= 0) {
            product.setPriceDecimal(this.price);
        }

        if (this.stock != null && this.stock >= 0) {
            product.setStock(this.stock);
        }
    }
}
