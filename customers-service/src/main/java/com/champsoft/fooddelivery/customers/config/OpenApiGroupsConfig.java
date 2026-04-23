package com.champsoft.fooddelivery.customers.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiGroupsConfig {

    @Bean
    GroupedOpenApi customersApi() {
        return GroupedOpenApi.builder()
                .group("customers")
                .pathsToMatch("/api/customers/**")
                .build();
    }
}
