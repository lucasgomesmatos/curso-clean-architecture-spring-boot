package com.catalogo.application.category.update;

import com.catalogo.application.category.create.CreateCategoryOutput;
import com.catalogo.domain.category.Category;
import com.catalogo.domain.category.CategoryGateway;
import com.catalogo.domain.category.CategoryID;
import com.catalogo.domain.exceptions.DomainException;
import com.catalogo.domain.validation.Error;
import com.catalogo.domain.validation.handler.Notification;
import io.vavr.API;
import io.vavr.control.Either;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultUpdateCategoryUseCase extends UpdateCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultUpdateCategoryUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<Notification, UpdateCategoryOutput> execute(final UpdateCategoryCommand aCommand) {

        final var anId = CategoryID.from(aCommand.id());
        final var aName = aCommand.name();
        final var aDescription = aCommand.description();
        final var isActive = aCommand.isActive();

        final var aCategory = this.categoryGateway.findById(anId).orElseThrow(notFound(anId));

        final var notification = Notification.create();

        aCategory
                .update(aName, aDescription, isActive)
                .validate(notification);


        return notification.hasErrors() ? API.Left(notification) : update(aCategory);
    }

    private Either<Notification, UpdateCategoryOutput> update(final Category aCategory) {
        return API.Try(() -> this.categoryGateway.update(aCategory))
                .toEither()
                .bimap(Notification::create, UpdateCategoryOutput::from);

    }

    private static Supplier<DomainException> notFound(final CategoryID anId) {
        return () -> DomainException.with(new Error("Category with id %s not found".formatted(anId.getValue())));
    }
}
