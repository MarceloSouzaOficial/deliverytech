package com.deliverytech.delivery_api.config;

import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

public class OpenApiConfig {
    
    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI()
        .info(new Info()
            .title("Delivery API")
            .description("Api para gerenciamento")
            .version("1.0")
        );

    }
}