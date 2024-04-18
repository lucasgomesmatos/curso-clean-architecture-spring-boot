package com.catalogo.domain.category;

import com.catalogo.domain.AggregateRoot;
import com.catalogo.domain.validation.ValidationHandler;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

public class Category extends AggregateRoot<CategoryID> {

    @Getter
    private String name;
    @Getter
    private String description;
    @Getter
    private Boolean active;
    @Getter
    private Instant createdAt;
    @Getter
    private Instant updatedAt;
    @Getter
    private Instant deletedAt;

    private Category(
            final CategoryID anId,
            final String aName,
            final String aDescription,
            final boolean isActive,
            final Instant aCreationDate,
            final Instant aUpdateDate,
            final Instant aDeleteDate) {
        super(anId);
        this.name = aName;
        this.description = aDescription;
        this.active = isActive;
        this.createdAt = aCreationDate;
        this.updatedAt = aUpdateDate;
        this.deletedAt = aDeleteDate;
    }

    public static Category newCategory(
            final String aName,
            final String aDescription,
            final boolean isActive
    ) {

        final var id = CategoryID.unique();
        final var now = Instant.now();
        final var deletedAt = isActive ? null : now;
        return new Category(
                id,
                aName,
                aDescription,
                isActive,
                now,
                now,
                deletedAt
        );
    }


    @Override
    public void validate(ValidationHandler handler) {
        new CategoryValidator(this, handler).validate();
    }

    public Category deactivate() {

        if (getDeletedAt() == null) {
            this.deletedAt = Instant.now();
        }

        this.active = false;
        this.updatedAt = Instant.now();
        return this;
    }

    public Category activate() {
        this.deletedAt = null;
        this.active = true;
        this.updatedAt = Instant.now();
        return this;
    }
}