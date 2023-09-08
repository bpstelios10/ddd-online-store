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
import org.stelios.courses.ddd.branches.application.StockService;
import org.stelios.courses.ddd.branches.repositories.StockEntity;
import org.stelios.courses.ddd.branches.repositories.StockId;

import java.util.stream.Stream;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StockControllerValidationTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private StockService service;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getStock_returnsStock() throws Exception {
        StockController.StockGetRequestBody requestBody = new StockController.StockGetRequestBody("branchId1", "productId1", 2);
        StockId stockId = new StockId(requestBody.getBranchId(), requestBody.getProductId(), requestBody.getSize());
        StockEntity entity = new StockEntity(requestBody.getBranchId(), requestBody.getProductId(), requestBody.getSize(), 5);
        when(service.getStockForBranch(stockId)).thenReturn(entity);

        ResultActions responseActions = mvc.perform(get("/branches/stocks")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody))
        );

        responseActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(entity)));
    }

    @ParameterizedTest
    @MethodSource("invalidStockIdValues")
    void getStock_fails_whenRequestBodyInvalid(String requestBody) throws Exception {
        ResultActions responseActions = mvc.perform(get("/branches/stocks")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        );

        responseActions.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    void saveStock_insertsStock() throws Exception {
        StockController.StockPostRequestBody requestBody = new StockController.StockPostRequestBody("branchId1", "productId1", 2, 1);
        StockEntity entity = new StockEntity(requestBody.getBranchId(), requestBody.getProductId(), requestBody.getSize(), requestBody.getQuantity());
        doNothing().when(service).save(entity);

        ResultActions responseActions = mvc.perform(post("/branches/stocks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestBody))
        );

        responseActions.andDo(print())
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @MethodSource("invalidStockEntityValues")
    void saveStock_fails_whenRequestBodyInvalid(String requestBody) throws Exception {
        ResultActions responseActions = mvc.perform(post("/branches/stocks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        );

        responseActions.andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    private static Stream<Arguments> invalidStockIdValues() {
        return Stream.of(
                Arguments.of("{\"branchId\":\"branchId1\",\"productId\":\"productId1\",\"size\":-1}"),
                Arguments.of("{\"branchId\":\"branchId1\",\"productId\":\"productId1\",\"size\":0}"),
                Arguments.of("{\"branchId\":\"branchId1\",\"productId\":\"productId1\"}"),
                Arguments.of("{\"branchId\":\"branchId1\",\"productId\":\"\",\"size\":3}"),
                Arguments.of("{\"branchId\":\"branchId1\",\"productId\":\" \",\"size\":3}"),
                Arguments.of("{\"branchId\":\"branchId1\",\"productId\":\"\t\",\"size\":3}"),
                Arguments.of("{\"branchId\":\"branchId1\",\"productId\":\"\n\",\"size\":3}"),
                Arguments.of("{\"branchId\":\"branchId1\",\"size\":3}"),
                Arguments.of("{\"branchId\":\"\",\"productId\":\"productId1\",\"size\":3}"),
                Arguments.of("{\"branchId\":\" \",\"productId\":\"productId1\",\"size\":3}"),
                Arguments.of("{\"branchId\":\"\t\",\"productId\":\"productId1\",\"size\":3}"),
                Arguments.of("{\"branchId\":\"\n\",\"productId\":\"productId1\",\"size\":3}"),
                Arguments.of("{\"productId\":\"productId1\",\"size\":3}")
        );
    }

    private static Stream<Arguments> invalidStockEntityValues() {
        return Stream.of(
                Arguments.of("{\"branchId\":\"branchId1\",\"productId\":\"productId1\",\"size\":-1,\"quantity\":2}"),
                Arguments.of("{\"branchId\":\"branchId1\",\"productId\":\"productId1\",\"size\":0,\"quantity\":2}"),
                Arguments.of("{\"branchId\":\"branchId1\",\"productId\":\"productId1\",\"quantity\":2}"),
                Arguments.of("{\"branchId\":\"branchId1\",\"productId\":\"productId1\",\"size\":3,\"quantity\":-1}"),
                Arguments.of("{\"branchId\":\"branchId1\",\"productId\":\"productId1\",\"size\":3,\"quantity\":0}"),
                Arguments.of("{\"branchId\":\"branchId1\",\"productId\":\"productId1\",\"size\":3}"),
                Arguments.of("{\"branchId\":\"branchId1\",\"productId\":\"\",\"size\":3,\"quantity\":2}"),
                Arguments.of("{\"branchId\":\"branchId1\",\"productId\":\" \",\"size\":3,\"quantity\":2}"),
                Arguments.of("{\"branchId\":\"branchId1\",\"productId\":\"\t\",\"size\":3,\"quantity\":2}"),
                Arguments.of("{\"branchId\":\"branchId1\",\"productId\":\"\n\",\"size\":3,\"quantity\":2}"),
                Arguments.of("{\"branchId\":\"branchId1\",\"size\":3,\"quantity\":2}"),
                Arguments.of("{\"branchId\":\"\",\"productId\":\"productId1\",\"size\":3,\"quantity\":2}"),
                Arguments.of("{\"branchId\":\" \",\"productId\":\"productId1\",\"size\":3,\"quantity\":2}"),
                Arguments.of("{\"branchId\":\"\t\",\"productId\":\"productId1\",\"size\":3,\"quantity\":2}"),
                Arguments.of("{\"branchId\":\"\n\",\"productId\":\"productId1\",\"size\":3,\"quantity\":2}"),
                Arguments.of("{\"productId\":\"productId1\",\"size\":3,\"quantity\":2}")
        );
    }
}
