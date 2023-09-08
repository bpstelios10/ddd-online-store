package org.stelios.courses.ddd.branches.repositories;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockId implements Serializable {
    private String branchId;
    private String productId;
    private int size;
}
