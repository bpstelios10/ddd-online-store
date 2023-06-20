package org.stelios.courses.ddd.products.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IProductRepository extends JpaRepository<SouvenirEntity, String> {

    List<SouvenirEntity> findAll();

    Optional<SouvenirEntity> findById(String id);
}
