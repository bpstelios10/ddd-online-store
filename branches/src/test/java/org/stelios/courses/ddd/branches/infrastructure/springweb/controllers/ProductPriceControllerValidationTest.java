package org.stelios.courses.ddd.branches.infrastructure.springweb.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.stelios.courses.ddd.branches.application.ProductPriceService;
import org.stelios.courses.ddd.branches.repositories.ProductPricesEntity;
import org.stelios.courses.ddd.branches.repositories.ProductPricesId;

import java.util.Optional;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductPriceControllerValidationTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private ProductPriceService service;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getProductPrices_returnsPrice_whenExists() throws Exception {
        ProductPricesId productPricesId = new ProductPricesId("branch1", "product1");
        ProductPricesEntity productPricesEntity = new ProductPricesEntity(productPricesId.getBranchId(), productPricesId.getProductId(), 13.99);
        when(service.getProductPrice(productPricesId)).thenReturn(Optional.of(productPricesEntity));
        ProductPriceController.ProductPricesGetRequestBody request =
                new ProductPriceController.ProductPricesGetRequestBody(productPricesId.getBranchId(), productPricesId.getProductId());

        ResultActions responseActions = mvc.perform(get("/branches/product-prices")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        );

        responseActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(productPricesEntity)));
    }

    @ParameterizedTest
    @MethodSource("invalidProductPriceRequestValues")
    void getProductPrices_fails_whenRequestBodyInvalid(String requestBody) throws Exception {
        when(service.getProductPrice(any())).thenReturn(Optional.empty());

        ResultActions responseActions = mvc.perform(get("/branches/product-prices")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        );

        responseActions.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    void saveProductPrices_succeeds() throws Exception {
        ProductPricesEntity entity = new ProductPricesEntity("branch1", "product1", 13.99);
        doNothing().when(service).save(entity);

        ProductPriceController.ProductPricesPostRequestBody request =
                new ProductPriceController.ProductPricesPostRequestBody(entity.getBranchId(), entity.getProductId(), entity.getPrice());
        ResultActions responseActions = mvc.perform(post("/branches/product-prices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        );

        responseActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @ParameterizedTest
    @MethodSource("invalidProductPriceEntityValues")
    void saveProductPrices_fails_whenRequestBodyInvalid(String requestBody) throws Exception {
        ResultActions responseActions = mvc.perform(post("/branches/product-prices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        );

        responseActions.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    private static Stream<Arguments> invalidProductPriceRequestValues() {
        return Stream.of(
                Arguments.of("{\"branchId\":\"branchId1\",\"productId\":\"\"}"),
                Arguments.of("{\"branchId\":\"branchId1\",\"productId\":\" \"}"),
                Arguments.of("{\"branchId\":\"branchId1\",\"productId\":\"\t\"}"),
                Arguments.of("{\"branchId\":\"branchId1\",\"productId\":\"\n\"}"),
                Arguments.of("{\"branchId\":\"branchId1\"}"),
                Arguments.of("{\"branchId\":\"\",\"productId\":\"productId1\"}"),
                Arguments.of("{\"branchId\":\" \",\"productId\":\"productId1\"}"),
                Arguments.of("{\"branchId\":\"\t\",\"productId\":\"productId1\"}"),
                Arguments.of("{\"branchId\":\"\n\",\"productId\":\"productId1\"}"),
                Arguments.of("{\"productId\":\"productId1\"}")
        );
    }

    private static Stream<Arguments> invalidProductPriceEntityValues() {
        return Stream.of(
                Arguments.of("{\"branchId\":\"branchId1\",\"productId\":\"productId1\",\"price\":-3.91}"),
                Arguments.of("{\"branchId\":\"branchId1\",\"productId\":\"productId1\",\"price\":0}"),
                Arguments.of("{\"branchId\":\"branchId1\",\"productId\":\"productId1\"}"),
                Arguments.of("{\"branchId\":\"branchId1\",\"productId\":\"\",\"price\":33.9}"),
                Arguments.of("{\"branchId\":\"branchId1\",\"productId\":\" \",\"price\":33.9}"),
                Arguments.of("{\"branchId\":\"branchId1\",\"productId\":\"\t\",\"price\":33.9}"),
                Arguments.of("{\"branchId\":\"branchId1\",\"productId\":\"\n\",\"price\":33.9}"),
                Arguments.of("{\"branchId\":\"branchId1\",\"price\":33.9}"),
                Arguments.of("{\"branchId\":\"\",\"productId\":\"productId1\",\"price\":33.9}"),
                Arguments.of("{\"branchId\":\" \",\"productId\":\"productId1\",\"price\":33.9}"),
                Arguments.of("{\"branchId\":\"\t\",\"productId\":\"productId1\",\"price\":33.9}"),
                Arguments.of("{\"branchId\":\"\n\",\"productId\":\"productId1\",\"price\":33.9}"),
                Arguments.of("{\"productId\":\"productId1\",\"price\":33.9}")
        );
    }
}
