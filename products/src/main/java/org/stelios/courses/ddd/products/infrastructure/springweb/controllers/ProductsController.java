package org.stelios.courses.ddd.products.infrastructure.springweb.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.stelios.courses.ddd.products.application.SouvenirService;
import org.stelios.courses.ddd.products.domain.Souvenir;
import org.stelios.courses.ddd.products.repositories.ProductAlreadyExistsException;

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
    public ResponseEntity<List<Souvenir>> getAllProducts() {
        log.info("request for all products");

        return ResponseEntity.ok(souvenirService.getAllProducts());
    }

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody Souvenir souvenir) throws ProductAlreadyExistsException {
        log.info("request:" + souvenir);

        souvenirService.save(souvenir);

        return ResponseEntity.ok().build();
    }
}
