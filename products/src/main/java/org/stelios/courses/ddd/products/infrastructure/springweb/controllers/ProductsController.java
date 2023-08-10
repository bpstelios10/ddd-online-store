package org.stelios.courses.ddd.products.infrastructure.springweb.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.stelios.courses.ddd.products.application.SouvenirService;
import org.stelios.courses.ddd.products.domain.IProduct;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductsController {

    private final SouvenirService souvenirService;

    public ProductsController(SouvenirService souvenirService) {
        this.souvenirService = souvenirService;
    }

    @GetMapping
    public ResponseEntity<List<IProduct>> getAllProducts() {
        log.info("request for all products");
        List<IProduct> products = new ArrayList<>();
        products.addAll(souvenirService.getAll());

        return ResponseEntity.ok(products);
    }
}
