package com.champsoft.fooddelivery.customers.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customersOpenApi() {
        return new OpenAPI().info(new Info()
                .title("Customers Service API")
                .version("1.0.0")
                .description("Food Delivery Platform - Customers REST API")
                .contact(new Contact()
                        .name("Food Delivery Team")
                        .email("n/a"))
                .license(new License().name("Apache 2.0")));
    }
}
