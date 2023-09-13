package org.stelios.courses.ddd.branches.repositories;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "productPrices")
@IdClass(ProductPricesId.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class ProductPricesEntity implements Serializable {
    @Id
    private String branchId;
    @Id
    private String productId;
    private double price;
}
