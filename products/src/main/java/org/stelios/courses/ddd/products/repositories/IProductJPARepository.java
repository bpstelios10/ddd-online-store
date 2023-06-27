package org.stelios.courses.ddd.products.repositories;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(value = "service.database.embedded", havingValue = "true", matchIfMissing = true)
public interface IProductJPARepository extends JpaRepository<SouvenirEntity, String>, IProductRepository {
}
