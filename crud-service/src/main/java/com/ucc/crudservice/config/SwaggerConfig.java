package com.ucc.crudservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

@Configuration
public class SwaggerConfig {

    @Value("classpath:swagger.yaml")
    private Resource yamlResource;

    @Bean
    public OpenAPI api() throws IOException {
        Yaml yaml = new Yaml(new Constructor(OpenAPI.class));
        return yaml.load(yamlResource.getInputStream());
    }
}