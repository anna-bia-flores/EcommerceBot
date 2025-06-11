package br.edu.ibmec.cloud.Ecommerce.services;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "azure.storage")
public class AzureStorageProperties {
    private String connectionString;
    private String containerName;
}
