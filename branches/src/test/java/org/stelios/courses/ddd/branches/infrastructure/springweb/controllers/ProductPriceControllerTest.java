package org.stelios.courses.ddd.branches.infrastructure.springweb.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.stelios.courses.ddd.branches.application.ProductPriceService;
import org.stelios.courses.ddd.branches.infrastructure.springweb.errors.ProductPriceNotFoundException;
import org.stelios.courses.ddd.branches.repositories.ProductPricesEntity;
import org.stelios.courses.ddd.branches.repositories.ProductPricesId;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductPriceControllerTest {

    @Mock
    private ProductPriceService service;
    @InjectMocks
    private ProductPriceController controller;

    @Test
    void getProductPrices_returnPrice_whenExists() throws ProductPriceNotFoundException {
        ProductPricesId productPricesId = new ProductPricesId("branch1", "product1");
        ProductPricesEntity productPricesEntity = new ProductPricesEntity(productPricesId.getBranchId(), productPricesId.getProductId(), 13.99);
        when(service.getProductPrice(productPricesId)).thenReturn(Optional.of(productPricesEntity));

        ProductPriceController.ProductPricesGetRequestBody request =
                new ProductPriceController.ProductPricesGetRequestBody(productPricesId.getBranchId(), productPricesId.getProductId());
        ResponseEntity<ProductPricesEntity> response = controller.getProductPrices(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(productPricesEntity);
        verifyNoMoreInteractions(service);
    }

    @Test
    void getProductPrices_return404_whenProductDoesntExist() {
        ProductPricesId productPricesId = new ProductPricesId("branch1", "product1");
        when(service.getProductPrice(productPricesId)).thenReturn(Optional.empty());

        ProductPriceController.ProductPricesGetRequestBody request =
                new ProductPriceController.ProductPricesGetRequestBody(productPricesId.getBranchId(), productPricesId.getProductId());
        assertThatThrownBy(() -> controller.getProductPrices(request))
                .isInstanceOf(ProductPriceNotFoundException.class)
                .hasMessage("Product price for product-id: [product1] and branch-id: [branch1] was not found");

        verifyNoMoreInteractions(service);
    }

    @Test
    void saveProductPrices_succeeds() {
        ProductPricesEntity entity = new ProductPricesEntity("branch1", "product1", 13.99);
        doNothing().when(service).save(entity);

        ProductPriceController.ProductPricesPostRequestBody request =
                new ProductPriceController.ProductPricesPostRequestBody(entity.getBranchId(), entity.getProductId(), entity.getPrice());
        ResponseEntity<Void> response = controller.saveProductPrices(request);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verifyNoMoreInteractions(service);
    }
}
