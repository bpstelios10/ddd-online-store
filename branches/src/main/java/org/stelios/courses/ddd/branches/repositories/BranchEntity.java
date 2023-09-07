package org.stelios.courses.ddd.branches.repositories;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BranchEntity {
    @Id
    protected String id;
    protected String description;
    protected String owner;
    protected String city;
}
