package org.stelios.courses.ddd.products.repositories;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "souvenirs")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SouvenirEntity extends ProductEntity {
    public SouvenirEntity(String id, String title, String description) {
        super(id, title, description);
    }
}
