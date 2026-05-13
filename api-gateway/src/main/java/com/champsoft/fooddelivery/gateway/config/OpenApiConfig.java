package com.champsoft.fooddelivery.gateway.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI gatewayOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Food Delivery Platform API Gateway")
                        .version("1.0.0")
                        .description("Single entry point for the Food Delivery Platform microservices")
                        .contact(new Contact()
                                .name("Food Delivery Team")
                                .email("n/a"))
                        .license(new License().name("Apache 2.0"))
                );
    }
}
