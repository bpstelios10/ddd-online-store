package org.stelios.courses.ddd.branches.infrastructure.springweb.errors;

import lombok.extern.slf4j.Slf4j;
import org.stelios.courses.ddd.branches.repositories.ProductPricesId;

@Slf4j
public class ProductPriceNotFoundException extends Exception {

    public ProductPriceNotFoundException(ProductPricesId productPriceId) {
        super("Product price for product-id: [" + productPriceId.getProductId() + "] and branch-id: [" + productPriceId.getBranchId() + "] was not found");
        log.error("Product price for product-id: [{}] and branch-id: [{}] was not found", productPriceId.getProductId(), productPriceId.getBranchId());
    }
}
