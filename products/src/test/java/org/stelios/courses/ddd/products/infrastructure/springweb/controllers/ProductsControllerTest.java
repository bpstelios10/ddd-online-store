package org.stelios.courses.ddd.products.infrastructure.springweb.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.stelios.courses.ddd.products.application.SouvenirService;
import org.stelios.courses.ddd.products.domain.Souvenir;
import org.stelios.courses.ddd.products.domain.SouvenirFactory;
import org.stelios.courses.ddd.products.repositories.ProductAlreadyExistsException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductsControllerTest {

    @Mock
    private SouvenirService souvenirService;
    @InjectMocks
    private ProductsController controller;

    @Test
    void getAllProducts_returnsProducts_whenSomeExist() {
        Souvenir souvenir1 = SouvenirFactory.create("id1", "title1", "description1");
        Souvenir souvenir2 = SouvenirFactory.create("id2", "title2", "description2");
        when(souvenirService.getAllProducts()).thenReturn(List.of(souvenir1, souvenir2));
        ResponseEntity<List<Souvenir>> expectedResponse = ResponseEntity.ok(List.of(souvenir1, souvenir2));

        ResponseEntity<List<Souvenir>> actualResponse = controller.getAllProducts();

        assertThat(expectedResponse).isEqualTo(actualResponse);
    }

    @Test
    void getAllProducts_returnsEmptyResponse_whenNoProducts() {
        when(souvenirService.getAllProducts()).thenReturn(List.of());
        ResponseEntity<List<Souvenir>> expectedResponse = ResponseEntity.ok(List.of());

        ResponseEntity<List<Souvenir>> actualResponse = controller.getAllProducts();

        assertThat(expectedResponse).isEqualTo(actualResponse);
    }

    @Test
    void create_returnsOk() throws ProductAlreadyExistsException {
        Souvenir souvenir1 = SouvenirFactory.create("id1", "title1", "description1");
        doNothing().when(souvenirService).save(souvenir1);
        ResponseEntity<Void> expectedResponse = ResponseEntity.ok().build();

        ResponseEntity<Void> actualResponse = controller.create(souvenir1);

        assertThat(expectedResponse).isEqualTo(actualResponse);
    }

    @Test
    void create_throwsException_whenProductExists() throws ProductAlreadyExistsException {
        Souvenir souvenir1 = SouvenirFactory.create("id1", "title1", "description1");
        doThrow(new ProductAlreadyExistsException()).when(souvenirService).save(souvenir1);

        assertThatThrownBy(() -> controller.create(souvenir1))
                .isInstanceOf(ProductAlreadyExistsException.class);
    }
}
