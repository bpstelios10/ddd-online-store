package org.stelios.courses.ddd.branches.repositories;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "physicalBranches")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PhysicalBranchEntity extends BranchEntity {

    private String address;

    public PhysicalBranchEntity(String id, String description, String owner, String city, String address) {
        super(id, description, owner, city);
        this.address = address;
    }
}
