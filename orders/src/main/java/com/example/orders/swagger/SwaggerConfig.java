package com.example.orders.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
@OpenAPIDefinition
public class SwaggerConfig {
    @Bean
    public OpenAPI api(){
        return  new  OpenAPI().info(new Info().title("Nuestra primera api crud creada por java/Spring").version("1")
                .contact(new Contact().name("Nahir Chandia").email("nahirchandia@gmail.com"))
                .description("Esta api te pemite administrar ordenes"));
    }
    }
