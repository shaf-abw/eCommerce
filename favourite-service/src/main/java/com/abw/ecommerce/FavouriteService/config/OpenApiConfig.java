package com.abw.ecommerce.FavouriteService.config;

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
                title = "FAVOURITE-SERVICE API DOCUMENTATIONS",
                description = "API Documentation's FAVOURITE-SERVICE",
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
                        .title("FAVOURITE-SERVICE API DOCUMENTATIONS")
                        .version("1.0.0")
                        .description("API Documentation's FAVOURITE-SERVICE")
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .name("Shafeeque Ahamed")
                                .url("https://github.com/shaf-abw")
                                .email("shafeeq.ease@gmail.com"))
                        .license(new io.swagger.v3.oas.models.info.License()
                                .name("MIT")
                                .url("https://mit-license.org/")));
    }

    @Bean
    public GroupedOpenApi userServiceApi() {
        return GroupedOpenApi.builder()
                .group("favourite-service")
                .pathsToMatch("/**") // Define the paths you want to include
                .build();
    }
}