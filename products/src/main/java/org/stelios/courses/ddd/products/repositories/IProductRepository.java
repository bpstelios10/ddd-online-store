package org.stelios.courses.ddd.products.repositories;

import java.util.List;
import java.util.Optional;

public interface IProductRepository {

    SouvenirEntity save(SouvenirEntity entity);

    List<SouvenirEntity> findAll();

    Optional<SouvenirEntity> findById(String id);
}
