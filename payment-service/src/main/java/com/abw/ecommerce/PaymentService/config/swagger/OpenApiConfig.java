package com.abw.ecommerce.PaymentService.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "PAYMENT-SERVICE API DOCUMENTATIONS",
                description = "API Documentation's PAYMENT-SERVICE",
                version = "1.0.0",
                contact = @Contact(
                        name = "Shafeeque Ahamed",
                        url = "https://github.com/shaf-abw",
                        email = "shafeeq.ease@gmail.com"
                ),
                license = @License(
                        name = "MIT",
                        url = "https://mit-license.org/"
                )
        )
)
@SecurityScheme(
        name = "JWT",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("PAYMENT-SERVICE API DOCUMENTATIONS")
                        .version("1.0.0")
                        .description("API Documentation's PAYMENT-SERVICE")
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .name("Shafeeque Ahamed")
                                .url("https://github.com/shaf-abw")
                                .email("shafeeq.ease@gmail.com"))
                        .license(new io.swagger.v3.oas.models.info.License()
                                .name("MIT")
                                .url("https://mit-license.org/")));
    }

    @Bean
    public GroupedOpenApi paymentServiceApi() {
        return GroupedOpenApi.builder()
                .group("payment-service")
                .pathsToMatch("/**") // Define the paths you want to include
                .build();
    }
}