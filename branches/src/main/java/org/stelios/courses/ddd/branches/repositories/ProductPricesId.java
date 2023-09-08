package org.stelios.courses.ddd.branches.repositories;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ProductPricesId implements Serializable {
    private final int branchId;
    private final int productId;
}
