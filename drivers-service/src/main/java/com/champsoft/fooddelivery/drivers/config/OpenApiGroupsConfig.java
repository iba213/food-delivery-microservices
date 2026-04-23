package com.champsoft.fooddelivery.drivers.config;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiGroupsConfig {

    @Bean
    GroupedOpenApi driversApi() {
        return GroupedOpenApi.builder()
                .group("drivers")
                .pathsToMatch("/api/drivers/**")
                .build();
    }
}