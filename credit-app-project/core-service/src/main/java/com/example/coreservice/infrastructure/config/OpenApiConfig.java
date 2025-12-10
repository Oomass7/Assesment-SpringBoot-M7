package com.example.coreservice.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI coreServiceOpenAPI() {
        Server server = new Server();
        server.setUrl("http://localhost:8082");
        server.setDescription("Core Service - Development");

        Contact contact = new Contact();
        contact.setName("Credit Management Team");
        contact.setEmail("support@creditmanagement.com");

        License license = new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT");

        Info info = new Info()
                .title("Core Service API")
                .version("1.0.0")
                .description("Microservicio principal de gestión de solicitudes de crédito con arquitectura hexagonal. " +
                        "Gestiona la creación y consulta de solicitudes, integrándose con el servicio de evaluación de riesgo.")
                .contact(contact)
                .license(license);

        SecurityScheme securityScheme = new SecurityScheme()
                .name("Bearer Authentication")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("Ingrese el token JWT obtenido del Auth Service");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("Bearer Authentication");

        return new OpenAPI()
                .info(info)
                .servers(List.of(server))
                .components(new Components().addSecuritySchemes("Bearer Authentication", securityScheme))
                .addSecurityItem(securityRequirement);
    }
}
