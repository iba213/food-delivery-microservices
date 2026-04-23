package com.champsoft.fooddelivery.orders.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiGroupsConfig {

    @Bean
    GroupedOpenApi ordersApi() {
        return GroupedOpenApi.builder()
                .group("orders")
                .pathsToMatch("/api/orders/**")
                .build();
    }
}