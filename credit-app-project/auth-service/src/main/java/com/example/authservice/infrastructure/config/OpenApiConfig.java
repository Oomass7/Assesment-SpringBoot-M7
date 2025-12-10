package com.example.authservice.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI authServiceOpenAPI() {
        Server server = new Server();
        server.setUrl("http://localhost:8081");
        server.setDescription("Auth Service - Development");

        Contact contact = new Contact();
        contact.setName("Credit Management Team");
        contact.setEmail("support@creditmanagement.com");

        License license = new License()
                .name("MIT License")
                .url("https://opensource.org/licenses/MIT");

        Info info = new Info()
                .title("Auth Service API")
                .version("1.0.0")
                .description("Microservicio de autenticación y gestión de usuarios con arquitectura hexagonal. " +
                        "Proporciona endpoints para registro, login y generación de tokens JWT.")
                .contact(contact)
                .license(license);

        return new OpenAPI()
                .info(info)
                .servers(List.of(server));
    }
}
