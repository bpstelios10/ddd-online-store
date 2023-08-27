package org.stelios.courses.ddd.products.infrastructure.springweb.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.stelios.courses.ddd.products.application.SouvenirService;
import org.stelios.courses.ddd.products.application.errors.ProductAlreadyExistsException;
import org.stelios.courses.ddd.products.repositories.SouvenirEntity;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/souvenirs")
public class SouvenirsController {

    private final SouvenirService souvenirService;

    public SouvenirsController(SouvenirService souvenirService) {
        this.souvenirService = souvenirService;
    }

    @GetMapping
    public ResponseEntity<List<SouvenirEntity>> getAll() {
        log.info("request for all souvenirs");

        return ResponseEntity.ok(souvenirService.getAll());
    }

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody SouvenirEntity souvenir) throws ProductAlreadyExistsException {
        log.info("request:" + souvenir);

        souvenirService.save(souvenir);

        return ResponseEntity.ok().build();
    }
}
