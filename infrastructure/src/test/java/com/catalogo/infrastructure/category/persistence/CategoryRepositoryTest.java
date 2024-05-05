package com.catalogo.infrastructure.category.persistence;

import com.catalogo.domain.category.Category;
import com.catalogo.infrastructure.MySQLGatewayTest;
import org.hibernate.PropertyValueException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

@MySQLGatewayTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void giverAnInvalidNameNullName_whenCallsSave_shouldReturnError() {

        final var expectedExceptionMessage = "not-null property references a null or transient value : com.catalogo.infrastructure.category.persistence.CategoryJpaEntity.name";
        final var expectedExceptionPropertyName = "name";

        final var category = Category.newCategory(
                "Filmes",
                "Filmes de ação",
                true
        );

        final var anEntity = CategoryJpaEntity.from(category);
        anEntity.setName(null);

        final var atualException = Assertions.assertThrows(DataIntegrityViolationException.class, () -> categoryRepository.save(anEntity));

        final var actualCause = Assertions.assertInstanceOf(PropertyValueException.class, atualException.getCause());

        Assertions.assertEquals(expectedExceptionPropertyName, actualCause.getPropertyName());
        Assertions.assertEquals(expectedExceptionMessage, actualCause.getMessage());

    }

    @Test
    void giverAnInvalidNullCreatedAt_whenCallsSave_shouldReturnError() {

        final var expectedExceptionMessage = "not-null property references a null or transient value : com.catalogo.infrastructure.category.persistence.CategoryJpaEntity.createdAt";
        final var expectedExceptionPropertyName = "createdAt";

        final var category = Category.newCategory(
                "Filmes",
                "Filmes de ação",
                true
        );

        final var anEntity = CategoryJpaEntity.from(category);
        anEntity.setCreatedAt(null);

        final var atualException = Assertions.assertThrows(DataIntegrityViolationException.class, () -> categoryRepository.save(anEntity));

        final var actualCause = Assertions.assertInstanceOf(PropertyValueException.class, atualException.getCause());

        Assertions.assertEquals(expectedExceptionPropertyName, actualCause.getPropertyName());
        Assertions.assertEquals(expectedExceptionMessage, actualCause.getMessage());

    }

    @Test
    void giverAnInvalidNullUpdateAt_whenCallsSave_shouldReturnError() {

        final var expectedExceptionMessage = "not-null property references a null or transient value : com.catalogo.infrastructure.category.persistence.CategoryJpaEntity.updatedAt";
        final var expectedExceptionPropertyName = "updatedAt";

        final var category = Category.newCategory(
                "Filmes",
                "Filmes de ação",
                true
        );

        final var anEntity = CategoryJpaEntity.from(category);
        anEntity.setUpdatedAt(null);

        final var atualException = Assertions.assertThrows(DataIntegrityViolationException.class, () -> categoryRepository.save(anEntity));

        final var actualCause = Assertions.assertInstanceOf(PropertyValueException.class, atualException.getCause());

        Assertions.assertEquals(expectedExceptionPropertyName, actualCause.getPropertyName());
        Assertions.assertEquals(expectedExceptionMessage, actualCause.getMessage());

    }
}
