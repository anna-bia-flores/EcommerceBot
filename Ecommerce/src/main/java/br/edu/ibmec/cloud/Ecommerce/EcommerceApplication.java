package br.edu.ibmec.cloud.Ecommerce;

import com.azure.spring.data.cosmos.repository.config.EnableCosmosRepositories;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableCosmosRepositories(basePackages = "br.edu.ibmec.cloud.Ecommerce.repositories.cosmos")
@PropertySource("classpath:.env.properties")
@EnableConfigurationProperties
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

}
