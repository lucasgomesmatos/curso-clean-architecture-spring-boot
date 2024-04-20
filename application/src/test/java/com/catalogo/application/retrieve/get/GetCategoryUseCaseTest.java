package com.catalogo.application.retrieve.get;

import com.catalogo.application.category.retrieve.get.CategoryOutput;
import com.catalogo.application.category.retrieve.get.DefaultGetCategoryUseCase;
import com.catalogo.domain.category.Category;
import com.catalogo.domain.category.CategoryGateway;
import com.catalogo.domain.category.CategoryID;
import com.catalogo.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class GetCategoryUseCaseTest {

    @InjectMocks
    private DefaultGetCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(categoryGateway);
    }


    @Test
    void givenAValidCommand_whenCallsGetCategory_shouldReturnCategory() {
        final var aCategory = Category.newCategory("Filmes", "Category Description 2", true);
        final var expectedId = aCategory.getId();
        final var expectedName = "Filmes";
        final var expectedDescription = "Category Description 2";
        final var expectedIsActive = true;

        Mockito.when(categoryGateway.findById(eq(expectedId))).thenReturn(Optional.of(Category.with(aCategory)));

        final var actualCategory = useCase.execute(expectedId.getValue());

        Assertions.assertEquals(CategoryOutput.from(aCategory), actualCategory);
        Assertions.assertEquals(expectedName, actualCategory.name());
        Assertions.assertEquals(expectedDescription, actualCategory.description());
        Assertions.assertEquals(aCategory.getCreatedAt(), actualCategory.createdAt());
        Assertions.assertEquals(aCategory.getUpdatedAt(), actualCategory.updatedAt());
        Assertions.assertNull(actualCategory.deletedAt());
        Assertions.assertEquals(expectedIsActive, actualCategory.isActive());

        Mockito.verify(categoryGateway, times(1)).findById(eq(expectedId));

    }

    @Test
    void givenAInvalidCommand_whenCallsGetCategory_shouldReturnCategory() {
        final var expectedErrorMessage = "Category with id 123 not found";
        final var expectedId = CategoryID.from("123");

        Mockito.when(categoryGateway.findById(eq(expectedId))).thenReturn(Optional.empty());

        final var actualException = Assertions.assertThrows(DomainException.class, () -> useCase.execute(expectedId.getValue()));
        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }

    @Test
    void givenAValidID_whenGatewayThrowsException_shouldReturnException() {
        final var expectedId = CategoryID.from("123");

        Mockito.doThrow(new IllegalStateException("Geteway error")).when(categoryGateway).findById(eq(expectedId));

        Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(expectedId.getValue()));
        Mockito.verify(categoryGateway, times(1)).findById(eq(expectedId));

    }
}
