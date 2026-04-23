package com.champsoft.fooddelivery.restaurants.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiGroupsConfig {

    @Bean
    GroupedOpenApi restaurantsApi() {
        return GroupedOpenApi.builder()
                .group("restaurants")
                .pathsToMatch("/api/restaurants/**")
                .build();
    }
}