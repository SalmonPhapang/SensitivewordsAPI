package com.service.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Sensitive Words API")
                        .version("1.0.0")
                        .description("API for checking and filtering sensitive words in chat messages, with CRUD management for the sensitive words database.")
                        .contact(new Contact()
                                .name("Development Team")
                                .email("dev-team@flash.co.za")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}
