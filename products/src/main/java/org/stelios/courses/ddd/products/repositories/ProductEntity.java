package org.stelios.courses.ddd.products.repositories;

import lombok.*;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@EqualsAndHashCode
public class ProductEntity {
    @Id
    private String id;
    private String title;
    private String description;
}
