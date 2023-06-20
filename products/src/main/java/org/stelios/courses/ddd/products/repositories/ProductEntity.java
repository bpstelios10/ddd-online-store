package org.stelios.courses.ddd.products.repositories;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
    @Id
    protected String id;
    protected String title;
    protected String description;
}
