package org.stelios.courses.ddd.products.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.stelios.courses.ddd.products.domain.Souvenir;
import org.stelios.courses.ddd.products.domain.SouvenirFactory;
import org.stelios.courses.ddd.products.repositories.IProductRepository;
import org.stelios.courses.ddd.products.repositories.ProductAlreadyExistsException;
import org.stelios.courses.ddd.products.repositories.SouvenirEntity;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SouvenirService {

    private final IProductRepository repository;

    @Autowired
    public SouvenirService(IProductRepository repository) {
        this.repository = repository;
    }

    public List<Souvenir> getAllProducts() {
        return repository.findAll()
                .stream()
                .map(e -> SouvenirFactory.create(e.getId(), e.getTitle(), e.getDescription()))
                .collect(Collectors.toList());
    }

    public void save(Souvenir souvenir) throws ProductAlreadyExistsException {
        if (repository.findById(souvenir.getId()).isPresent()) throw new ProductAlreadyExistsException();

        repository.save(new SouvenirEntity(souvenir.getId(), souvenir.getTitle(), souvenir.getDescription()));
    }
}
