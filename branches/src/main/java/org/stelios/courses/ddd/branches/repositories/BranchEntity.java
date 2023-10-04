package org.stelios.courses.ddd.branches.repositories;

import lombok.*;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode
public class BranchEntity {
    @Id
    private String id;
    private String description;
    private String owner;
    private String city;
}
