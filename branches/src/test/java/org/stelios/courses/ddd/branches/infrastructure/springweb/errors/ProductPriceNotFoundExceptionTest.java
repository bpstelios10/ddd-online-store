package org.stelios.courses.ddd.branches.infrastructure.springweb.errors;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.stelios.courses.ddd.branches.repositories.ProductPricesId;

import static org.stelios.courses.ddd.branches.utils.AssertionUtils.assertContainsInLogs;
import static org.stelios.courses.ddd.branches.utils.AssertionUtils.getListAppenderForClass;

class ProductPriceNotFoundExceptionTest {

    private ListAppender<ILoggingEvent> loggingEventListAppender;

    @BeforeEach
    void setUp() {
        loggingEventListAppender = getListAppenderForClass(ProductPriceNotFoundException.class);
    }

    @Test
    void ProductPriceNotFoundException_printsErrorLog() {
        new ProductPriceNotFoundException(new ProductPricesId("branch1", "product1"));

        assertContainsInLogs(loggingEventListAppender,
                "Product price for product-id: [product1] and branch-id: [branch1] was not found",
                Level.ERROR);
    }
}
