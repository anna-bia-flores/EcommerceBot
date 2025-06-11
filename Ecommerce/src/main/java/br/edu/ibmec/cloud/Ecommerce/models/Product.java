package br.edu.ibmec.cloud.Ecommerce.models;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@Container(containerName = "products")
public class Product {

    @Id
    private String id;



    private List<String> imageUrls = new ArrayList<>();

    @PartitionKey
    private String category;

    private String name;
    private String description;

    private String price = "0.00"; // ← armazenado como String no CosmosDB

    private int stock;

    public BigDecimal getPriceDecimal() {
        return new BigDecimal(Optional.ofNullable(price).orElse("0.00"));
    }

    public void setPriceDecimal(BigDecimal newPrice) {
        this.price = newPrice != null ? newPrice.toPlainString() : "0.00";
    }

    public boolean isInStock(int quantity) {
        return stock >= quantity;
    }

    public void removeFromStock(int quantity) {
        if (!isInStock(quantity)) {
            throw new IllegalStateException("Insufficient stock");
        }
        this.stock -= quantity;
    }

    public void updatePrice(BigDecimal newPrice) {
        if (newPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Price must be positive");
        }
        setPriceDecimal(newPrice);
    }

    public void addStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
        this.stock += quantity;
    }
}
