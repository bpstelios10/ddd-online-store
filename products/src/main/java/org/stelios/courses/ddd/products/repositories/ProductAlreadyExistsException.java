package org.stelios.courses.ddd.products.repositories;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProductAlreadyExistsException extends Exception {

    private static final String MESSAGE = "Trying to save existing product";

    public ProductAlreadyExistsException() {
        super(MESSAGE);
        log.error(MESSAGE);
    }
}
