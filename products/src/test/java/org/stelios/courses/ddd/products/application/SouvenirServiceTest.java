package org.stelios.courses.ddd.products.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.stelios.courses.ddd.products.domain.Souvenir;
import org.stelios.courses.ddd.products.domain.SouvenirFactory;
import org.stelios.courses.ddd.products.repositories.IProductRepository;
import org.stelios.courses.ddd.products.repositories.ProductAlreadyExistsException;
import org.stelios.courses.ddd.products.repositories.SouvenirEntity;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SouvenirServiceTest {

    @Mock
    private IProductRepository repository;
    @InjectMocks
    private SouvenirService souvenirService;

    @Test
    void getAll_returnsSouvenirs_whenSomeExist() {
        SouvenirEntity souvenirEntity1 = new SouvenirEntity("id1", "title1", "description1");
        SouvenirEntity souvenirEntity2 = new SouvenirEntity("id2", "title2", "description2");
        Souvenir souvenir1 = SouvenirFactory.create("id1", "title1", "description1");
        Souvenir souvenir2 = SouvenirFactory.create("id2", "title2", "description2");
        when(repository.findAll()).thenReturn(List.of(souvenirEntity1, souvenirEntity2));
        List<Souvenir> expectedResponse = List.of(souvenir1, souvenir2);

        List<Souvenir> actualResponse = souvenirService.getAll();

        assertThat(expectedResponse).isEqualTo(actualResponse);
    }

    @Test
    void getAll_returnsEmptyResponse_whenNoSouvenirs() {
        when(repository.findAll()).thenReturn(List.of());
        List<Souvenir> expectedResponse = List.of();

        List<Souvenir> actualResponse = souvenirService.getAll();

        assertThat(expectedResponse).isEqualTo(actualResponse);
    }

    @Test
    void save_returnsOk() {
        SouvenirEntity souvenirEntity1 = new SouvenirEntity("id1", "title1", "description1");
        Souvenir souvenir1 = SouvenirFactory.create("id1", "title1", "description1");
        when(repository.findById("id1")).thenReturn(Optional.empty());
        when(repository.save(souvenirEntity1)).thenReturn(souvenirEntity1);

        assertDoesNotThrow(() -> souvenirService.save(souvenir1));
    }

    @Test
    void save_throwsException_whenSouvenirExists() {
        SouvenirEntity souvenirEntity1 = new SouvenirEntity("id1", "title1", "description1");
        Souvenir souvenir1 = SouvenirFactory.create("id1", "title1", "description1");
        when(repository.findById("id1")).thenReturn(Optional.of(souvenirEntity1));

        assertThatThrownBy(() -> souvenirService.save(souvenir1))
                .isInstanceOf(ProductAlreadyExistsException.class);
    }
}
