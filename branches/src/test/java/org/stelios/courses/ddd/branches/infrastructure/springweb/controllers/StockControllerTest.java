package org.stelios.courses.ddd.branches.infrastructure.springweb.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.stelios.courses.ddd.branches.application.StockService;
import org.stelios.courses.ddd.branches.repositories.StockEntity;
import org.stelios.courses.ddd.branches.repositories.StockId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StockControllerTest {

    @Mock
    private StockService service;
    @InjectMocks
    private StockController controller;

    @Test
    void getStock_returnsStock() {
        StockController.StockGetRequestBody requestBody = new StockController.StockGetRequestBody("branchId1", "productId1", 2);
        StockId stockId = new StockId(requestBody.getBranchId(), requestBody.getProductId(), requestBody.getSize());
        StockEntity entity = new StockEntity(requestBody.getBranchId(), requestBody.getProductId(), requestBody.getSize(), 5);
        when(service.getStockForBranch(stockId)).thenReturn(entity);

        ResponseEntity<StockEntity> responseEntity = controller.getStock(requestBody);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        assertThat(responseEntity.getBody()).isEqualTo(entity);
        verifyNoMoreInteractions(service);
    }

    @Test
    void saveStock_insertsStock() {
        StockController.StockPostRequestBody requestBody = new StockController.StockPostRequestBody("branchId1", "productId1", 2, 1);
        StockEntity entity = new StockEntity(requestBody.getBranchId(), requestBody.getProductId(), requestBody.getSize(), requestBody.getQuantity());
        doNothing().when(service).save(entity);

        ResponseEntity<Void> responseEntity = controller.saveStock(requestBody);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
        verifyNoMoreInteractions(service);
    }
}
