package org.stelios.courses.ddd.products.repositories;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "souvenirs")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class SouvenirEntity extends ProductEntity {
    public SouvenirEntity(String id, String title, String description) {
        super(id, title, description);
    }
}
