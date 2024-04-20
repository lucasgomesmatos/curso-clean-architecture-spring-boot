package com.catalogo.application.category.retrieve.get;

import com.catalogo.domain.category.CategoryGateway;
import com.catalogo.domain.category.CategoryID;
import com.catalogo.domain.exceptions.DomainException;
import com.catalogo.domain.validation.Error;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultGetCategoryUseCase extends GetCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultGetCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public CategoryOutput execute(String anId) {
        CategoryID anCategoryID = CategoryID.from(anId);

        return this.categoryGateway.findById(anCategoryID)
                .map(CategoryOutput::from)
                .orElseThrow(notFound(anCategoryID));
    }

    private static Supplier<DomainException> notFound(final CategoryID anId) {
        return () -> DomainException.with(new Error("Category with id %s not found".formatted(anId.getValue())));
    }
}
