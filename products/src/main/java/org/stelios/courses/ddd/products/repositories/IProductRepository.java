package org.stelios.courses.ddd.products.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.stelios.courses.ddd.products.domain.IProduct;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<IProduct, String> {

    List<IProduct> findAll();
}
