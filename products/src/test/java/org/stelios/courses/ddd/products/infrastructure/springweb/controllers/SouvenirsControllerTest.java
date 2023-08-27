package org.stelios.courses.ddd.products.infrastructure.springweb.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.stelios.courses.ddd.products.application.SouvenirService;
import org.stelios.courses.ddd.products.application.errors.ProductAlreadyExistsException;
import org.stelios.courses.ddd.products.repositories.SouvenirEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SouvenirsControllerTest {

    @Mock
    private SouvenirService souvenirService;
    @InjectMocks
    private SouvenirsController controller;

    @Test
    void getAll_returnsSouvenirs_whenSomeExist() {
        SouvenirEntity souvenir1 = new SouvenirEntity("id1", "title1", "description1");
        SouvenirEntity souvenir2 = new SouvenirEntity("id2", "title2", "description2");
        when(souvenirService.getAll()).thenReturn(List.of(souvenir1, souvenir2));
        ResponseEntity<List<SouvenirEntity>> expectedResponse = ResponseEntity.ok(List.of(souvenir1, souvenir2));

        ResponseEntity<List<SouvenirEntity>> actualResponse = controller.getAll();

        assertThat(expectedResponse).isEqualTo(actualResponse);
    }

    @Test
    void getAll_returnsEmptyResponse_whenNoSouvenirs() {
        when(souvenirService.getAll()).thenReturn(List.of());
        ResponseEntity<List<SouvenirEntity>> expectedResponse = ResponseEntity.ok(List.of());

        ResponseEntity<List<SouvenirEntity>> actualResponse = controller.getAll();

        assertThat(expectedResponse).isEqualTo(actualResponse);
    }

    @Test
    void create_returnsOk() throws ProductAlreadyExistsException {
        SouvenirEntity souvenir1 = new SouvenirEntity("id1", "title1", "description1");
        doNothing().when(souvenirService).save(souvenir1);
        ResponseEntity<Void> expectedResponse = ResponseEntity.ok().build();

        ResponseEntity<Void> actualResponse = controller.create(souvenir1);

        assertThat(expectedResponse).isEqualTo(actualResponse);
    }

    @Test
    void create_throwsException_whenSouvenirExists() throws ProductAlreadyExistsException {
        SouvenirEntity souvenir1 = new SouvenirEntity("id1", "title1", "description1");
        doThrow(new ProductAlreadyExistsException()).when(souvenirService).save(souvenir1);

        assertThatThrownBy(() -> controller.create(souvenir1))
                .isInstanceOf(ProductAlreadyExistsException.class);
    }
}
