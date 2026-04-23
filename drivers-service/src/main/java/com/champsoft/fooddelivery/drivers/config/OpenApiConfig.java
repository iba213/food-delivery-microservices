package com.champsoft.fooddelivery.drivers.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI driversServiceOpenApi() {
        return new OpenAPI().info(new Info()
                .title("Drivers Service API")
                .description("Microservice responsible for driver management, availability, and delivery operations")
                .version("1.0.0"));
    }
}