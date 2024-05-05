package com.catalogo.infrastructure.configuration.usecases;

import com.catalogo.application.category.create.CreateCategoryUseCase;
import com.catalogo.application.category.create.DefaultCreateCategoryUseCase;
import com.catalogo.application.category.delete.DefaultDeleteCategoryUseCase;
import com.catalogo.application.category.delete.DeleteCategoryUseCase;
import com.catalogo.application.category.retrieve.get.DefaultGetCategoryUseCase;
import com.catalogo.application.category.retrieve.get.GetCategoryUseCase;
import com.catalogo.application.category.retrieve.list.DefaultListCategoriesUseCase;
import com.catalogo.application.category.retrieve.list.ListCategoriesUseCase;
import com.catalogo.application.category.update.DefaultUpdateCategoryUseCase;
import com.catalogo.application.category.update.UpdateCategoryUseCase;
import com.catalogo.domain.category.CategoryGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryUseCaseConfig {

    private final CategoryGateway categoryGateway;

    public CategoryUseCaseConfig(final CategoryGateway categoryGateway) {
        this.categoryGateway = categoryGateway;
    }

    @Bean
    public CreateCategoryUseCase createCategoryUseCase() {
        return new DefaultCreateCategoryUseCase(categoryGateway);
    }

    @Bean
    public UpdateCategoryUseCase updateCategoryUseCase() {
        return new DefaultUpdateCategoryUseCase(categoryGateway);
    }

    @Bean
    public GetCategoryUseCase getCategoryUseCase() {
        return new DefaultGetCategoryUseCase(categoryGateway);
    }

    @Bean
    public ListCategoriesUseCase listCategoriesUseCase() {
        return new DefaultListCategoriesUseCase(categoryGateway);
    }

    @Bean
    public DeleteCategoryUseCase deleteCategoryUseCase() {
        return new DefaultDeleteCategoryUseCase(categoryGateway);
    }
}
