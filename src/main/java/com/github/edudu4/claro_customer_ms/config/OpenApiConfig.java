package com.github.edudu4.claro_customer_ms.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Claro Customer MS API",
                version = "1.0.0",
                description = "Microsserviço responsável pelo gerenciamento de clientes com autenticação OAuth2 e mensageria RabbitMQ.",
                contact = @Contact(
                        name = "Eduardo Alves",
                        url = "https://github.com/edudu4"
                ),
                license = @License(
                        name = "Uso para desafio técnico"
                )
        ),
        security = @SecurityRequirement(name = "bearerAuth")
)
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfig {
}