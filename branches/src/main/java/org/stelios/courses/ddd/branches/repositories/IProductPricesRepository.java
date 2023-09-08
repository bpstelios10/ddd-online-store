package org.stelios.courses.ddd.branches.repositories;

import java.util.List;
import java.util.Optional;

public interface IProductPricesRepository {

    ProductPricesEntity save(ProductPricesEntity entity);

    List<ProductPricesEntity> findAll();

    Optional<ProductPricesEntity> findById(ProductPricesId id);
}
