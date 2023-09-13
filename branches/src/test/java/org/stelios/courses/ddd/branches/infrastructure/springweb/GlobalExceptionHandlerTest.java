package org.stelios.courses.ddd.branches.infrastructure.springweb;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.stelios.courses.ddd.branches.application.errors.BranchAlreadyExistsException;
import org.stelios.courses.ddd.branches.infrastructure.springweb.errors.ProductPriceNotFoundException;
import org.stelios.courses.ddd.branches.repositories.ProductPricesId;

import static org.assertj.core.api.Assertions.assertThat;
import static org.stelios.courses.ddd.branches.utils.AssertionUtils.assertContainsOnlyInLogs;
import static org.stelios.courses.ddd.branches.utils.AssertionUtils.getListAppenderForClass;

class GlobalExceptionHandlerTest {

    private ListAppender<ILoggingEvent> loggingEventListAppender;
    private final GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

    @BeforeEach
    void setUp() {
        loggingEventListAppender = getListAppenderForClass(GlobalExceptionHandler.class);
    }

    @Test
    void handleExceptions_returnsCorrectResponseAndMessage() {
        ResponseEntity<Void> response = globalExceptionHandler.handleExceptions(new Exception("test exception"));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
        assertContainsOnlyInLogs(loggingEventListAppender,
                "Unexpected Exception: test exception",
                Level.ERROR);
    }

    @Test
    void handleBranchAlreadyExistsException_returnsCorrectResponseAndMessage() {
        ResponseEntity<String> response = globalExceptionHandler.handleProductAlreadyExistsException(new BranchAlreadyExistsException());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo("Branch with this id already exists");
        assertContainsOnlyInLogs(loggingEventListAppender,
                "Handling exception: [Trying to save existing branch]",
                Level.DEBUG);
    }

    @Test
    void handleProductPriceNotFoundException_returnsCorrectResponseAndMessage() {
        ResponseEntity<String> response =
                globalExceptionHandler.handleProductPriceNotFoundException(new ProductPriceNotFoundException(new ProductPricesId("branch1", "product1")));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
        assertContainsOnlyInLogs(loggingEventListAppender,
                "Handling exception: [Product price for product-id: [product1] and branch-id: [branch1] was not found]",
                Level.DEBUG);
    }
}
