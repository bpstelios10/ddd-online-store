package org.stelios.courses.ddd.branches.infrastructure.springweb.controllers;

import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.stelios.courses.ddd.branches.application.StockService;
import org.stelios.courses.ddd.branches.repositories.StockEntity;
import org.stelios.courses.ddd.branches.repositories.StockId;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Slf4j
@RestController
@RequestMapping("/branches/stocks")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public ResponseEntity<StockEntity> getStock(@Valid @RequestBody StockGetRequestBody requestBody) {
        log.info("request for stock");

        StockId stockId = new StockId(requestBody.branchId, requestBody.productId, requestBody.size);
        StockEntity stockForBranch = stockService.getStockForBranch(stockId);

        return ResponseEntity.ok(stockForBranch);
    }

    @PostMapping
    public ResponseEntity<Void> saveStock(@Valid @RequestBody StockPostRequestBody requestBody) {
        log.info("request for inserting/updating stock");
        StockEntity entity = new StockEntity(requestBody.getBranchId(), requestBody.getProductId(), requestBody.getSize(), requestBody.quantity);

        stockService.save(entity);

        return ResponseEntity.ok().build();
    }

    @Data
    static class StockGetRequestBody {
        @NotBlank(message = "Branch ID may not be missing or empty")
        private final String branchId;
        @NotBlank(message = "Product Id may not be missing or empty")
        private final String productId;
        @Positive(message = "Size may not be 0 or negative")
        private final int size;
    }

    @Getter
    static class StockPostRequestBody extends StockGetRequestBody {
        @Positive(message = "Quantity may not be 0 or negative")
        private final int quantity;

        StockPostRequestBody(String branchId, String productId, int size, int quantity) {
            super(branchId, productId, size);
            this.quantity = quantity;
        }
    }
}
