package org.stelios.courses.ddd.branches.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.stelios.courses.ddd.branches.repositories.*;

import java.util.Optional;

@Slf4j
@Component
public class ProductPriceService {

    private final IProductPricesRepository repository;

    public ProductPriceService(IProductPricesRepository repository) {
        this.repository = repository;
    }

    public Optional<ProductPricesEntity> getProductPrice(ProductPricesId productPricesId) {
        return repository.findById(productPricesId);
    }

    public void save(ProductPricesEntity entity) {
        ProductPricesId productPricesId = new ProductPricesId(entity.getBranchId(), entity.getProductId());
        if (repository.findById(productPricesId).isPresent())
            log.debug("updating existing product price to: [{}]", productPricesId);
        else
            log.debug("inserting product price: [{}]", productPricesId);

        repository.save(entity);
    }
}
