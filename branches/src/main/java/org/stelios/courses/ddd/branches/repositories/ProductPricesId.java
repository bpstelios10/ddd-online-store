package org.stelios.courses.ddd.branches.repositories;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductPricesId implements Serializable {
    private int branchId;
    private int productId;
}
