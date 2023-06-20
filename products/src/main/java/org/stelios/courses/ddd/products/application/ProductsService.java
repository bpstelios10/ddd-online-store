package org.stelios.courses.ddd.products.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.stelios.courses.ddd.products.domain.IProduct;
import org.stelios.courses.ddd.products.repositories.IProductRepository;

import java.util.List;

@Component
public class ProductsService {

    private final IProductRepository repository;

    @Autowired
    public ProductsService(IProductRepository repository) {
        this.repository = repository;
    }

    public List<IProduct> getAllProducts() {
        return repository.findAll();
    }
}
