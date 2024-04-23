package com.catalogo.infrastructure;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.AbstractEnvironment;

@OpenAPIDefinition(info = @Info(title = "API", version = "v1"))
@SpringBootApplication(scanBasePackages = {"com.catalogo.*"})
public class Application {
    public static void main(String[] args) {
       // System.setProperty(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME, "development");
        SpringApplication.run(Application.class, args);
    }
}