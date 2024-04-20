package com.catalogo.application.update;

import com.catalogo.application.category.create.CreateCategoryCommand;
import com.catalogo.application.category.update.DefaultUpdateCategoryUseCase;
import com.catalogo.application.category.update.UpdateCategoryCommand;
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

import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class UpdateCategoryUseCaseTest {

    @InjectMocks
    private DefaultUpdateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(categoryGateway);
    }
    // 1 - Teste do caminho feliz
    // 2 - Teste passando uma proprietade inválida
    // 3 - Teste atualizando uma categoria para inativa
    // 4 - Teste simulando uma erro genérico vindo do gateway
    // 5 - Teste atualizar categoria passando ID inválido

    @Test
    void givenAValidCommand_whenCallsUpdateCategory_shouldReturnCategoryId() {
        final var aCategory = Category.newCategory("Film", "Filmes", true);
        final var expectedName = "Filmes 2";
        final var expectedDescription = "Category Description 2";
        final var expectedIsActive = true;

        final var expectedId = aCategory.getId();


        final var aCommand = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        Mockito.when(categoryGateway.findById(Mockito.eq(expectedId))).thenReturn(Optional.of(Category.with(aCategory)));

        Mockito.when(categoryGateway.update(Mockito.any())).thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(categoryGateway, times(1)).findById(eq(expectedId));
        Mockito.verify(categoryGateway, times(1)).update(Mockito.argThat(aUpdateCategory ->
                Objects.equals(expectedName, aUpdateCategory.getName()) &&
                        Objects.equals(expectedDescription, aUpdateCategory.getDescription()) &&
                        Objects.equals(expectedIsActive, aUpdateCategory.getActive()) &&
                        Objects.equals(expectedId, aUpdateCategory.getId()) &&
                        Objects.equals(aCategory.getCreatedAt(), aUpdateCategory.getCreatedAt()) &&
                        aCategory.getUpdatedAt().isBefore(aUpdateCategory.getUpdatedAt()) &&
                        Objects.isNull(aUpdateCategory.getDeletedAt())
        ));
    }

    @Test
    void givenAInvalidName_whenCallsUpdateCategory_shouldReturnDomainException() {
        final var aCategory = Category.newCategory("Film", "Filmes", true);

        final String expectedName = null;
        final var expectedDescription = "Category Description";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' should not be null";
        final var expectedErrorCount = 1;
        final var expectedId = aCategory.getId();

        final var aCommand = UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedIsActive);

        Mockito.when(categoryGateway.findById(Mockito.eq(expectedId))).thenReturn(Optional.of(Category.with(aCategory)));

        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());
        Mockito.verify(categoryGateway, times(0)).update(any());


    }


    @Test
    void givenAValidInactiveCommand_whenCallsUpdateCategory_shouldReturninactiveCategoryId() {
        final var aCategory = Category.newCategory("Film", "Filmes", true);
        final var expectedName = "Filmes 2";
        final var expectedDescription = "Category Description 2";
        final var expectedIsActive = false;

        final var expectedId = aCategory.getId();


        final var aCommand = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        Mockito.when(categoryGateway.findById(Mockito.eq(expectedId))).thenReturn(Optional.of(Category.with(aCategory)));

        Mockito.when(categoryGateway.update(Mockito.any())).thenAnswer(returnsFirstArg());

        Assertions.assertTrue(aCategory.getActive());
        Assertions.assertNull(aCategory.getDeletedAt());

        final var actualOutput = useCase.execute(aCommand).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(categoryGateway, times(1)).findById(eq(expectedId));
        Mockito.verify(categoryGateway, times(1)).update(Mockito.argThat(aUpdateCategory ->
                Objects.equals(expectedName, aUpdateCategory.getName()) &&
                        Objects.equals(expectedDescription, aUpdateCategory.getDescription()) &&
                        Objects.equals(expectedIsActive, aUpdateCategory.getActive()) &&
                        Objects.equals(expectedId, aUpdateCategory.getId()) &&
                        Objects.equals(aCategory.getCreatedAt(), aUpdateCategory.getCreatedAt()) &&
                        aCategory.getUpdatedAt().isBefore(aUpdateCategory.getUpdatedAt()) &&
                        Objects.nonNull(aUpdateCategory.getDeletedAt())
        ));
    }


    @Test
    void givenAValidCommand_whenGatewayThrowsRandomException_shouldReturnAException() {
        final var aCategory = Category.newCategory("Film", "Filmes", true);
        final var expectedName = "Category Name";
        final var expectedDescription = "Category Description";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "Gateway Error";
        final var expectedErrorCount = 1;

        final var expectedId = aCategory.getId();

        final var aCommand = UpdateCategoryCommand.with(
                expectedId.getValue(),
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        Mockito.when(categoryGateway.findById(Mockito.eq(expectedId))).thenReturn(Optional.of(Category.with(aCategory)));

        Mockito.when(categoryGateway.update(any())).thenThrow(new IllegalStateException(expectedErrorMessage));

        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        Mockito.verify(categoryGateway, times(1)).findById(eq(expectedId));
        Mockito.verify(categoryGateway, times(1)).update(Mockito.argThat(aUpdateCategory ->
                Objects.equals(expectedName, aUpdateCategory.getName()) &&
                        Objects.equals(expectedDescription, aUpdateCategory.getDescription()) &&
                        Objects.equals(expectedIsActive, aUpdateCategory.getActive()) &&
                        Objects.equals(expectedId, aUpdateCategory.getId()) &&
                        Objects.equals(aCategory.getCreatedAt(), aUpdateCategory.getCreatedAt()) &&
                        aCategory.getUpdatedAt().isBefore(aUpdateCategory.getUpdatedAt()) &&
                        Objects.isNull(aUpdateCategory.getDeletedAt())
        ));
    }


    @Test
    void givenACommandWithInvalidID_whenCallsUpdateCategory_shouldReturnNotFoundException() {
        final var expectedName = "Filmes 2";
        final var expectedDescription = "Category Description 2";
        final var expectedIsActive = false;
        final var expectedId = "123";
        final var expectedErrorMessage = "Category with id %s not found".formatted(expectedId);
        final var expectedErrorCount = 1;

        final var aCommand = UpdateCategoryCommand.with(
                expectedId,
                expectedName,
                expectedDescription,
                expectedIsActive
        );

        Mockito.when(categoryGateway.findById(Mockito.eq(CategoryID.from(expectedId)))).thenReturn(Optional.empty());

        final var atualException = Assertions.assertThrows(DomainException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorMessage, atualException.getErrors().getFirst().message());
        Assertions.assertEquals(expectedErrorCount, atualException.getErrors().size());

        Mockito.verify(categoryGateway, times(1)).findById(eq(CategoryID.from(expectedId)));
        Mockito.verify(categoryGateway, times(0)).update(Mockito.any());
    }
}
