package org.stelios.courses.ddd.branches.infrastructure.springweb.controllers;

import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.stelios.courses.ddd.branches.application.ProductPriceService;
import org.stelios.courses.ddd.branches.infrastructure.springweb.errors.ProductPriceNotFoundException;
import org.stelios.courses.ddd.branches.repositories.ProductPricesEntity;
import org.stelios.courses.ddd.branches.repositories.ProductPricesId;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/branches/product-prices")
public class ProductPriceController {

    private final ProductPriceService productPriceService;

    public ProductPriceController(ProductPriceService productPriceService) {
        this.productPriceService = productPriceService;
    }

    @GetMapping
    public ResponseEntity<ProductPricesEntity> getProductPrices(@Valid @RequestBody ProductPricesGetRequestBody requestBody) throws ProductPriceNotFoundException {
        log.info("request for product price");

        ProductPricesId productPriceId = new ProductPricesId(requestBody.branchId, requestBody.productId);
        Optional<ProductPricesEntity> productPrice = productPriceService.getProductPrice(productPriceId);
        if (productPrice.isEmpty()) throw new ProductPriceNotFoundException(productPriceId);

        return ResponseEntity.ok(productPrice.get());
    }

    @PostMapping
    public ResponseEntity<Void> saveProductPrices(@Valid @RequestBody ProductPricesPostRequestBody requestBody) {
        log.info("request for inserting/updating product price");
        ProductPricesEntity entity = new ProductPricesEntity(requestBody.getBranchId(), requestBody.getProductId(), requestBody.getPrice());

        productPriceService.save(entity);

        return ResponseEntity.ok().build();
    }

    @Data
    static class ProductPricesGetRequestBody {
        @NotBlank(message = "Branch ID may not be missing or empty")
        private final String branchId;
        @NotBlank(message = "Product Id may not be missing or empty")
        private final String productId;
    }

    @Getter
    static class ProductPricesPostRequestBody extends ProductPricesGetRequestBody {
        @Positive(message = "Product price may not be 0 or negative")
        private final double price;

        ProductPricesPostRequestBody(String branchId, String productId, double price) {
            super(branchId, productId);
            this.price = price;
        }
    }
}
