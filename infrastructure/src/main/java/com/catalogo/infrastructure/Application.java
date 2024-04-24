package com.catalogo.infrastructure;

import com.catalogo.domain.category.Category;
import com.catalogo.infrastructure.category.persistence.CategoryJpaEntity;
import com.catalogo.infrastructure.category.persistence.CategoryRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.AbstractEnvironment;

import java.util.List;

@OpenAPIDefinition(info = @Info(title = "API", version = "v1"))
@SpringBootApplication(scanBasePackages = {"com.catalogo.*"})
public class Application {
    public static void main(String[] args) {
        System.setProperty(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME, "development");
        SpringApplication.run(Application.class, args);
    }



    ApplicationRunner runner(CategoryRepository categoryRepository) {
        return args -> {

            List<CategoryJpaEntity> all = categoryRepository.findAll();

            Category filmes = Category.newCategory("Filmes", null, true);

            categoryRepository.save(CategoryJpaEntity.from(filmes));
        };
    }
}