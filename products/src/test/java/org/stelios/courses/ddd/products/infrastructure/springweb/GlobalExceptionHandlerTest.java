package org.stelios.courses.ddd.products.infrastructure.springweb;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.stelios.courses.ddd.products.repositories.ProductAlreadyExistsException;

import static org.assertj.core.api.Assertions.assertThat;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @Test
    void handleExceptions_returnsCorrectResponseAndMessage() {
        ResponseEntity<Void> response = globalExceptionHandler.handleExceptions(new Exception("test exception"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void handleEventAlreadyExistsException_returnsCorrectResponseAndMessage() {
        ResponseEntity<String> response = globalExceptionHandler.handleProductAlreadyExistsException(new ProductAlreadyExistsException());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Product with this id already exists");
    }
}
