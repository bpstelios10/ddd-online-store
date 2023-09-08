package org.stelios.courses.ddd.branches.repositories;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "productPrices")
@Data
@IdClass(ProductPricesId.class)
public class ProductPricesEntity implements Serializable {
    @Id
    private final int branchId;
    @Id
    private final int productId;
    private final float price;
}
