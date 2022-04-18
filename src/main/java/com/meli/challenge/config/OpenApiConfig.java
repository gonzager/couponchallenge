package com.meli.challenge.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Value;

@Configuration
public class OpenApiConfig {
    private final String appApiName;
    private final String appApiVersion;

    public OpenApiConfig(@Value("${app.api.name}") String appApiName , @Value("${app.api.version}") String appApiVersion) {
        this.appApiName = appApiName;
        this.appApiVersion = appApiVersion;
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info().title(appApiName).version(appApiVersion));

    }
}
