package com.champsoft.fooddelivery.restaurants.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI restaurantsServiceOpenApi() {
        return new OpenAPI().info(new Info()
                .title("Restaurants Service API")
                .description("Microservice responsible for restaurant management, menu items, and availability")
                .version("1.0.0"));
    }
}