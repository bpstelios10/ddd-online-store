package org.stelios.courses.ddd.branches.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.stelios.courses.ddd.branches.repositories.IStockRepository;
import org.stelios.courses.ddd.branches.repositories.StockEntity;
import org.stelios.courses.ddd.branches.repositories.StockId;

@Slf4j
@Component
public class StockService {

    private final IStockRepository repository;

    public StockService(IStockRepository repository) {
        this.repository = repository;
    }

    public StockEntity getStockForBranch(StockId stockId) {
        return repository.findById(stockId)
                .orElse(new StockEntity(stockId.getBranchId(), stockId.getProductId(), stockId.getSize(), 0));
    }

    public void save(StockEntity entity) {
        StockId stockId = new StockId(entity.getBranchId(), entity.getProductId(), entity.getSize());
        if (repository.findById(stockId).isPresent())
            log.debug("updating stock: [{}]", stockId);
        else
            log.debug("inserting stock: [{}]", stockId);

        repository.save(entity);
    }
}
