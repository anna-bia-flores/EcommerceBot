package br.edu.ibmec.cloud.Ecommerce.useCases.product;

import br.edu.ibmec.cloud.Ecommerce.models.Product;
import br.edu.ibmec.cloud.Ecommerce.repositories.cosmos.ProductCosmosRepository;
import br.edu.ibmec.cloud.Ecommerce.services.AzureBlobStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class AddProductImageUseCase {

    private final ProductCosmosRepository productRepository;
    private final AzureBlobStorageService blobService;

    public AddProductImageUseCase(ProductCosmosRepository productRepository, AzureBlobStorageService blobService) {
        this.productRepository = productRepository;
        this.blobService = blobService;
    }

    public Product execute(String productId, MultipartFile file) {
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new IllegalArgumentException("Product not found");
        }

        Product product = optionalProduct.get();
        String imageUrl = blobService.uploadFile(file);

        product.getImageUrls().add(imageUrl);
        return productRepository.save(product);
    }
}
