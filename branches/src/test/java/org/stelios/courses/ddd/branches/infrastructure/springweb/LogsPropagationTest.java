package org.stelios.courses.ddd.branches.infrastructure.springweb;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.stelios.courses.ddd.branches.application.ProductPriceService;
import org.stelios.courses.ddd.branches.infrastructure.springweb.controllers.ProductPriceController;
import org.stelios.courses.ddd.branches.infrastructure.springweb.errors.ProductPriceNotFoundException;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.stelios.courses.ddd.branches.utils.AssertionUtils.assertContainsOnlyInLogs;
import static org.stelios.courses.ddd.branches.utils.AssertionUtils.getListAppenderForClass;

@SpringBootTest
@AutoConfigureMockMvc
class LogsPropagationTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private ProductPriceService service;
    private ListAppender<ILoggingEvent> globalExceptionHandlerLogs;
    private ListAppender<ILoggingEvent> productPriceControllerLogs;
    private ListAppender<ILoggingEvent> productPriceNotFoundExceptionLogs;

    @BeforeEach
    void setUp() {
        globalExceptionHandlerLogs = getListAppenderForClass(GlobalExceptionHandler.class);
        productPriceNotFoundExceptionLogs = getListAppenderForClass(ProductPriceNotFoundException.class);
        productPriceControllerLogs = getListAppenderForClass(ProductPriceController.class);
    }

    @Test
    void logs_getPrinted_whenDifferentClassesAreInvolved() throws Exception {
        when(service.getProductPrice(any())).thenReturn(Optional.empty());

        ResultActions responseActions = mvc.perform(get("/branches/product-prices")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"branchId\":\"branchId1\",\"productId\":\"productId1\"}")
        );

        responseActions.andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
        assertContainsOnlyInLogs(globalExceptionHandlerLogs,
                "Handling exception: [Product price for product-id: [productId1] and branch-id: [branchId1] was not found]",
                Level.DEBUG);
        assertContainsOnlyInLogs(productPriceNotFoundExceptionLogs,
                "Product price for product-id: [productId1] and branch-id: [branchId1] was not found",
                Level.ERROR);
        assertContainsOnlyInLogs(productPriceControllerLogs,
                "request for product price",
                Level.INFO);
    }
}
