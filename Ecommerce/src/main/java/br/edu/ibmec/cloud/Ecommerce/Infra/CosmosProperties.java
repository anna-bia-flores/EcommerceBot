package br.edu.ibmec.cloud.Ecommerce.Infra;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "azure.cosmos")
public class CosmosProperties {
    private String uri;
    private String key;
    private String database;
    private boolean queryMetricsEnabled;
    private boolean responseDiagnosticsEnabled;
}
