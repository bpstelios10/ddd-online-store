package org.stelios.courses.ddd.products.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.stelios.courses.ddd.products.application.errors.ProductAlreadyExistsException;
import org.stelios.courses.ddd.products.repositories.IProductRepository;
import org.stelios.courses.ddd.products.repositories.SouvenirEntity;

import java.util.List;

@Component
public class SouvenirService {

    private final IProductRepository repository;

    @Autowired
    public SouvenirService(IProductRepository repository) {
        this.repository = repository;
    }

    public List<SouvenirEntity> getAll() {
        return repository.findAll();
    }

    public void save(SouvenirEntity souvenir) throws ProductAlreadyExistsException {
        if (repository.findById(souvenir.getId()).isPresent()) throw new ProductAlreadyExistsException();

        repository.save(souvenir);
    }
}
