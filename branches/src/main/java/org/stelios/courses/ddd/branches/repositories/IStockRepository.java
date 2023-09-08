package org.stelios.courses.ddd.branches.repositories;

import java.util.List;
import java.util.Optional;

public interface IStockRepository {

    StockEntity save(StockEntity entity);

    List<StockEntity> findAll();

    Optional<StockEntity> findById(StockId id);
}
