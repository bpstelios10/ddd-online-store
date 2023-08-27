package org.stelios.courses.ddd.products.infrastructure.springweb;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.stelios.courses.ddd.products.application.errors.ProductAlreadyExistsException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Void> handleExceptions(Exception ex) {
        log.error("Unexpected Exception: {}", ex.getMessage(), ex);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }

    @ExceptionHandler({ProductAlreadyExistsException.class})
    public ResponseEntity<String> handleProductAlreadyExistsException(ProductAlreadyExistsException ex) {
        log.debug("Handling exception: [{}]", ex.getMessage());

        return ResponseEntity.ok("Product with this id already exists");
    }
}
