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
@Table(name = "branchStock")
@IdClass(StockId.class)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class StockEntity implements Serializable {
    @Id
    private String branchId;
    @Id
    private String productId;
    @Id
    private int size;
    private int quantity;
}
