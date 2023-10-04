package org.stelios.courses.ddd.branches.repositories;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class ProductPricesId implements Serializable {
    private String branchId;
    private String productId;
}
