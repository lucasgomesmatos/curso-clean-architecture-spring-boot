package com.catalogo.domain.category;

import com.catalogo.domain.exceptions.DomainException;
import com.catalogo.domain.validation.handler.ThowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CategoryTest {

    @Test
    void givenAValidParams_whenCallNewCategory_thenInstantiateACategory() {

        final var expectedName = "Filmes";
        final var expectedDescription = "Filmes em geral";
        final var expectedIsActive = true;

        final var atualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertNotNull(atualCategory);
        Assertions.assertNotNull(atualCategory.getId());
        Assertions.assertEquals(expectedName, atualCategory.getName());
        Assertions.assertEquals(expectedDescription, atualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, atualCategory.getActive());

        Assertions.assertNotNull(atualCategory.getCreatedAt());
        Assertions.assertNotNull(atualCategory.getUpdatedAt());
        Assertions.assertNull(atualCategory.getDeletedAt());
    }

    @Test
    void givenAnInvalidNullName_whenCallNewCategoryAndValidate_thenShouldReceiveAnError() {

        final String expectedName = null;
        final var expectedDescription = "Filmes em geral";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;

        final var atualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var actualException = Assertions.assertThrows(DomainException.class, () -> atualCategory.validate(new ThowsValidationHandler()));
        Assertions.assertEquals(actualException.getErrors().get(0).message(), expectedErrorMessage);
        Assertions.assertEquals(actualException.getErrors().size(), expectedErrorCount);

    }

    @Test
    void givenAnInvalidEmptyName_whenCallNewCategoryAndValidate_thenShouldReceiveAnError() {

        final String expectedName = "";
        final var expectedDescription = "Filmes em geral";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be empty";
        final var expectedErrorCount = 1;

        final var atualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var actualException = Assertions.assertThrows(DomainException.class, () -> atualCategory.validate(new ThowsValidationHandler()));
        Assertions.assertEquals(actualException.getErrors().get(0).message(), expectedErrorMessage);
        Assertions.assertEquals(actualException.getErrors().size(), expectedErrorCount);

    }

    @Test
    void givenAnInvalidNameLengthLessThan3_whenCallNewCategoryAndValidate_thenShouldReceiveAnError() {

        final String expectedName = "Fi ";
        final var expectedDescription = "Filmes em geral";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";
        final var expectedErrorCount = 1;

        final var atualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var actualException = Assertions.assertThrows(DomainException.class, () -> atualCategory.validate(new ThowsValidationHandler()));
        Assertions.assertEquals(actualException.getErrors().get(0).message(), expectedErrorMessage);
        Assertions.assertEquals(actualException.getErrors().size(), expectedErrorCount);

    }

    @Test
    void givenAnInvalidNameLengthMoreThan255_whenCallNewCategoryAndValidate_thenShouldReceiveAnError() {

        final String expectedName = """
                Evidentemente, a estrutura atual da organização acarreta um processo de reformulação e modernização dos métodos utilizados na avaliação de resultados.
                É claro que o surgimento do comércio virtual desafia a capacidade de equalização das formas de ação.
                Evidentemente, a estrutura atual da organização acarreta um processo de reformulação e modernização dos métodos utilizados na avaliação de resultados.
                É claro que o surgimento do comércio virtual desafia a capacidade de equalização das formas de ação.
                Evidentemente, a estrutura atual da organização acarreta um processo de reformulação e modernização dos métodos utilizados na avaliação de resultados.
                É claro que o surgimento do comércio virtual desafia a capacidade de equalização das formas de ação.
                """;
        final var expectedDescription = "Filmes em geral";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";
        final var expectedErrorCount = 1;

        final var atualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var actualException = Assertions.assertThrows(DomainException.class, () -> atualCategory.validate(new ThowsValidationHandler()));
        Assertions.assertEquals(actualException.getErrors().get(0).message(), expectedErrorMessage);
        Assertions.assertEquals(actualException.getErrors().size(), expectedErrorCount);

    }


    @Test
    void givenAnInvalidEmptyDescription_whenCallNewCategoryAndValidate_thenShouldReceiveAnError() {

        final String expectedName = "Filmes em geral";
        final var expectedDescription = " ";
        final var expectedIsActive = true;

        final var atualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);


        Assertions.assertDoesNotThrow(() -> atualCategory.validate(new ThowsValidationHandler()));
        Assertions.assertNotNull(atualCategory);
        Assertions.assertNotNull(atualCategory.getId());
        Assertions.assertEquals(expectedName, atualCategory.getName());
        Assertions.assertEquals(expectedDescription, atualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, atualCategory.getActive());

        Assertions.assertNotNull(atualCategory.getCreatedAt());
        Assertions.assertNotNull(atualCategory.getUpdatedAt());
        Assertions.assertNull(atualCategory.getDeletedAt());

    }

    @Test
    void givenAValidFalseIsActive_whenCallNewCategoryAndValidate_thenShouldReceiveAnError() {

        final String expectedName = "Filmes em geral";
        final var expectedDescription = "A categoria";
        final var expectedIsActive = false;

        final var atualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);


        Assertions.assertDoesNotThrow(() -> atualCategory.validate(new ThowsValidationHandler()));
        Assertions.assertNotNull(atualCategory);
        Assertions.assertNotNull(atualCategory.getId());
        Assertions.assertEquals(expectedName, atualCategory.getName());
        Assertions.assertEquals(expectedDescription, atualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, atualCategory.getActive());

        Assertions.assertNotNull(atualCategory.getCreatedAt());
        Assertions.assertNotNull(atualCategory.getUpdatedAt());
        Assertions.assertNotNull(atualCategory.getDeletedAt());

    }

    @Test
    void givenAValidActive_whenCallDeactivate_thenReturnDeactivateCategoryInactivated() {

        final String expectedName = "Filmes em geral";
        final var expectedDescription = "A categoria";
        final var expectedIsActive = false;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, true);

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThowsValidationHandler()));

        final var createdAt = aCategory.getCreatedAt();
        final var updatedAt = aCategory.getUpdatedAt();

        Assertions.assertNull(aCategory.getDeletedAt());
        Assertions.assertTrue(aCategory.getActive());

        final var atualCategory = aCategory.deactivate();

        Assertions.assertDoesNotThrow(() -> atualCategory.validate(new ThowsValidationHandler()));


        Assertions.assertEquals(aCategory.getId(), atualCategory.getId());
        Assertions.assertEquals(expectedName, atualCategory.getName());
        Assertions.assertEquals(expectedDescription, atualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, atualCategory.getActive());

        Assertions.assertNotNull(atualCategory.getCreatedAt());
        Assertions.assertEquals(createdAt, atualCategory.getCreatedAt());
        Assertions.assertTrue(atualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNotNull(atualCategory.getDeletedAt());

    }

    @Test
    void givenAValidInactiveCategory_whenCallActivate_thenReturnCategoryActivated() {

        final String expectedName = "Filmes em geral";
        final var expectedDescription = "A categoria";
        final var expectedIsActive = true;

        final var aCategory = Category.newCategory(expectedName, expectedDescription, false);

        Assertions.assertDoesNotThrow(() -> aCategory.validate(new ThowsValidationHandler()));

        final var createdAt = aCategory.getCreatedAt();
        final var updatedAt = aCategory.getUpdatedAt();

        Assertions.assertNotNull(aCategory.getDeletedAt());
        Assertions.assertFalse(aCategory.getActive());

        final var atualCategory = aCategory.activate();

        Assertions.assertDoesNotThrow(() -> atualCategory.validate(new ThowsValidationHandler()));


        Assertions.assertEquals(aCategory.getId(), atualCategory.getId());
        Assertions.assertEquals(expectedName, atualCategory.getName());
        Assertions.assertEquals(expectedDescription, atualCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, atualCategory.getActive());

        Assertions.assertEquals(createdAt, atualCategory.getCreatedAt());
        Assertions.assertNotNull(atualCategory.getCreatedAt());
        Assertions.assertTrue(atualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(atualCategory.getDeletedAt());

    }

}