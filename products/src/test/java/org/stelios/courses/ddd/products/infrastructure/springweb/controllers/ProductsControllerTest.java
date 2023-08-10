package org.stelios.courses.ddd.products.infrastructure.springweb.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.stelios.courses.ddd.products.application.SouvenirService;
import org.stelios.courses.ddd.products.domain.IProduct;
import org.stelios.courses.ddd.products.domain.Souvenir;
import org.stelios.courses.ddd.products.domain.SouvenirFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

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
        when(souvenirService.getAll()).thenReturn(List.of(souvenir1, souvenir2));
        ResponseEntity<List<Souvenir>> expectedResponse = ResponseEntity.ok(List.of(souvenir1, souvenir2));

        ResponseEntity<List<IProduct>> actualResponse = controller.getAllProducts();

        assertThat(expectedResponse).isEqualTo(actualResponse);
    }

    @Test
    void getAllProducts_returnsEmptyResponse_whenNoProducts() {
        when(souvenirService.getAll()).thenReturn(List.of());
        ResponseEntity<List<Souvenir>> expectedResponse = ResponseEntity.ok(List.of());

        ResponseEntity<List<IProduct>> actualResponse = controller.getAllProducts();

        assertThat(expectedResponse).isEqualTo(actualResponse);
    }
}
