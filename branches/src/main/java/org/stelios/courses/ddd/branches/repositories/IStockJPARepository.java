package org.stelios.courses.ddd.branches.repositories;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(value = "service.database.embedded", havingValue = "true")
public interface IStockJPARepository extends JpaRepository<StockEntity, StockId>, IStockRepository {
}
