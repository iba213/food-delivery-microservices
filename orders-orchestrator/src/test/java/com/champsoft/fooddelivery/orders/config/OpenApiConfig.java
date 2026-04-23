package com.champsoft.fooddelivery.orders.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI ordersOrchestratorOpenApi() {
        return new OpenAPI().info(new Info()
                .title("Orders Orchestrator API")
                .description("Microservice responsible for order orchestration, customer orders, restaurant coordination, payment details, and driver assignment")
                .version("1.0.0"));
    }
}