package br.edu.ibmec.cloud.Ecommerce.services;

import com.azure.storage.blob.BlobClientBuilder;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobContainerClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class AzureBlobStorageService {


    private final BlobContainerClient containerClient;

    public AzureBlobStorageService(AzureStorageProperties props) {

        this.containerClient = new BlobContainerClientBuilder()
                .connectionString(props.getConnectionString())
                .containerName(props.getContainerName())
                .buildClient();

        if (!containerClient.exists()) {
            containerClient.create();
        }
    }
    public String uploadFile(MultipartFile file) {
        try {
            String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();
            var blobClient = containerClient.getBlobClient(filename);

            try (InputStream inputStream = file.getInputStream()) {
                blobClient.upload(inputStream, file.getSize(), true);
            }

            return blobClient.getBlobUrl();

        } catch (Exception e) {
            throw new RuntimeException("Failed to upload to Azure Blob", e);
        }
    }
}
