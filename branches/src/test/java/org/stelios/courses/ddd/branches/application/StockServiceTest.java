package org.stelios.courses.ddd.branches.application;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.stelios.courses.ddd.branches.repositories.IStockRepository;
import org.stelios.courses.ddd.branches.repositories.StockEntity;
import org.stelios.courses.ddd.branches.repositories.StockId;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {

    @Mock
    private IStockRepository repository;
    @InjectMocks
    private StockService service;

    @Test
    void getStockForBranch_returnsStock_whenExists() {
        StockId stockId = new StockId("branchId1", "productId1", 42);
        StockEntity stock = new StockEntity(stockId.getBranchId(), stockId.getProductId(), stockId.getSize(), 5);
        when(repository.findById(stockId)).thenReturn(Optional.of(stock));

        StockEntity stockForBranch = service.getStockForBranch(stockId);

        assertThat(stockForBranch).isEqualTo(stock);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void getStockForBranch_returnsZeroQuantity_whenNoStockExists() {
        StockId stockId = new StockId("branchId1", "productId1", 42);
        StockEntity stock = new StockEntity(stockId.getBranchId(), stockId.getProductId(), stockId.getSize(), 0);
        when(repository.findById(stockId)).thenReturn(Optional.empty());

        StockEntity stockForBranch = service.getStockForBranch(stockId);

        assertThat(stockForBranch).isEqualTo(stock);
        verifyNoMoreInteractions(repository);
    }

    @Test
    void save_inserts_whenNoStockExists() {
        StockId stockId = new StockId("branchId1", "productId1", 42);
        StockEntity entity = new StockEntity(stockId.getBranchId(), stockId.getProductId(), stockId.getSize(), 4);
        when(repository.findById(stockId)).thenReturn(Optional.empty());
        when(repository.save(entity)).thenReturn(entity);

        service.save(entity);

        verifyNoMoreInteractions(repository);
    }

    @Test
    void save_updates_whenNoStockExists() {
        StockId stockId = new StockId("branchId1", "productId1", 42);
        StockEntity entity = new StockEntity(stockId.getBranchId(), stockId.getProductId(), stockId.getSize(), 4);
        when(repository.findById(stockId)).thenReturn(Optional.of(entity));
        when(repository.save(entity)).thenReturn(entity);

        service.save(entity);

        verifyNoMoreInteractions(repository);
    }
}
