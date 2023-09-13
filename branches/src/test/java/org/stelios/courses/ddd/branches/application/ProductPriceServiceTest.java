package org.stelios.courses.ddd.branches.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.stelios.courses.ddd.branches.repositories.IProductPricesRepository;
import org.stelios.courses.ddd.branches.repositories.ProductPricesEntity;
import org.stelios.courses.ddd.branches.repositories.ProductPricesId;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductPriceServiceTest {

    @Mock
    private IProductPricesRepository repository;
    @InjectMocks
    private ProductPriceService service;

    @Test
    void getProductPrice_returnsProductPrice_whenExists() {
        ProductPricesId productPricesId = new ProductPricesId("branchId1", "productId1");
        ProductPricesEntity entity = new ProductPricesEntity(productPricesId.getBranchId(), productPricesId.getProductId(), 55);
        when(repository.findById(productPricesId)).thenReturn(Optional.of(entity));

        Optional<ProductPricesEntity> productPrice = service.getProductPrice(productPricesId);

        assertThat(productPrice).isNotEmpty();
        assertThat(productPrice.get()).isEqualTo(entity);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void getProductPrice_returnsZeroQuantity_whenNoProductPriceExists() {
        ProductPricesId productPricesId = new ProductPricesId("branchId1", "productId1");
        when(repository.findById(productPricesId)).thenReturn(Optional.empty());

        Optional<ProductPricesEntity> productPrice = service.getProductPrice(productPricesId);

        assertThat(productPrice).isEmpty();
        verifyNoMoreInteractions(repository);
    }

    @Test
    void save_inserts_whenNoProductPriceExists() {
        ProductPricesId productPriceId = new ProductPricesId("branchId1", "productId1");
        ProductPricesEntity entity = new ProductPricesEntity(productPriceId.getBranchId(), productPriceId.getProductId(), 74);
        when(repository.findById(productPriceId)).thenReturn(Optional.empty());
        when(repository.save(entity)).thenReturn(entity);

        service.save(entity);

        verifyNoMoreInteractions(repository);
    }

    @Test
    void save_updates_whenNoProductPriceExists() {
        ProductPricesId productPriceId = new ProductPricesId("branchId1", "productId1");
        ProductPricesEntity existingEntity = new ProductPricesEntity(productPriceId.getBranchId(), productPriceId.getProductId(), 74);
        ProductPricesEntity newEntity = new ProductPricesEntity(productPriceId.getBranchId(), productPriceId.getProductId(), 60);
        when(repository.findById(productPriceId)).thenReturn(Optional.of(existingEntity));
        when(repository.save(newEntity)).thenReturn(newEntity);

        service.save(newEntity);

        verifyNoMoreInteractions(repository);
    }
}
