package pe.upeu.edu.biblibackend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI matriculaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Backend de Matrícula - EduAndes")
                        .description("Documentación de los servicios REST para el sistema de gestión académica y matrículas.")
                        .version("v1.0.0"));
    }
}