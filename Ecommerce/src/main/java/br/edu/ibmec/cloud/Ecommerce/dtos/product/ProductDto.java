package br.edu.ibmec.cloud.Ecommerce.dtos.product;

import br.edu.ibmec.cloud.Ecommerce.models.Product;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {

    private String id;
    private String name;
    private String description;
    private String category;
    private String price;
    private int stock;

    public static ProductDto fromEntity(Product product) {
        ProductDto dto = new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setCategory(product.getCategory());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        return dto;
    }
}
