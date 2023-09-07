package org.stelios.courses.ddd.branches.repositories;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "branchStock")
@Data
@EqualsAndHashCode
public class StockEntity implements Serializable {
    @Id
    private final int branchId;
    @Id
    private final int productId;
    @Id
    private final int size;
    private final int quantity;
}