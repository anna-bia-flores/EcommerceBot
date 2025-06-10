package br.edu.ibmec.cloud.Ecommerce.Infra;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.net.URI;

@Component
public class SwaggerAutoLauncher {

    @PostConstruct
    public void openSwaggerUi() {
        try {
            String swaggerUrl = "http://localhost:8080/swagger-ui/index.html";
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(swaggerUrl));
                System.out.println("✅ Swagger UI aberto automaticamente.");
            } else {
                System.out.println("⚠️ Desktop não suportado. URL do Swagger: " + swaggerUrl);
            }
        } catch (Exception e) {
            System.err.println("Erro ao tentar abrir Swagger UI: " + e.getMessage());
        }
    }
}
