package br.edu.ibmec.cloud.Ecommerce.Infra;


import br.edu.ibmec.cloud.Ecommerce.Infra.CosmosProperties;
import com.azure.cosmos.CosmosClientBuilder;
import com.azure.cosmos.DirectConnectionConfig;
import com.azure.spring.data.cosmos.config.AbstractCosmosConfiguration;
import com.azure.spring.data.cosmos.config.CosmosConfig;
import com.azure.spring.data.cosmos.repository.config.EnableCosmosRepositories;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableConfigurationProperties(CosmosProperties.class)
@EnableCosmosRepositories(basePackages = "br.edu.ibmec.cloud.ecommerce_cloud.repository.cosmos")
@PropertySource("classpath:application.properties")
public class CosmosConfiguration extends AbstractCosmosConfiguration {

    private CosmosProperties properties;

    public CosmosConfiguration(CosmosProperties properties) {
        this.properties = properties;
    }

    @Bean
    public CosmosClientBuilder cosmosClientBuilder() {
        return new CosmosClientBuilder()
                .endpoint(properties.getUri())
                .key(properties.getKey())
                .directMode(DirectConnectionConfig.getDefaultConfig());
    }

    @Bean
    public CosmosConfig cosmosConfig() {
        return CosmosConfig.builder().build();
    }

    @Override
    protected String getDatabaseName() {
        return properties.getDatabase();
    }
}
