package com.service.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${api.info.title}")
    private String apiTitle;

    @Value("${api.info.version}")
    private String apiVersion;

    @Value("${api.info.description}")
    private String apiDescription;

    @Value("${api.info.contact.name}")
    private String contactName;

    @Value("${api.info.contact.email}")
    private String contactEmail;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(apiTitle)
                        .version(apiVersion)
                        .description(apiDescription)
                        .contact(new Contact()
                                .name(contactName)
                                .email(contactEmail)))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"));
    }
}
