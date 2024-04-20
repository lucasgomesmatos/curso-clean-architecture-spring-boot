package com.catalogo.application.category.retrieve.list;

import com.catalogo.application.UseCase;
import com.catalogo.domain.category.CategorySearchQuery;
import com.catalogo.domain.pagination.Pagination;

public abstract class ListCategoriesUseCase extends UseCase<CategorySearchQuery, Pagination<CategoryListOutput>> {
}
