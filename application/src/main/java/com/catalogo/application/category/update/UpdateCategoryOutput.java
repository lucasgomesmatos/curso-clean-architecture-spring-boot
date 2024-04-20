package com.catalogo.application.category.update;

import com.catalogo.application.category.create.CreateCategoryOutput;
import com.catalogo.domain.category.Category;
import com.catalogo.domain.category.CategoryID;

public record UpdateCategoryOutput(
        CategoryID id
) {
    public static UpdateCategoryOutput from(final Category aCategory) {
        return new UpdateCategoryOutput(aCategory.getId());
    }
}
