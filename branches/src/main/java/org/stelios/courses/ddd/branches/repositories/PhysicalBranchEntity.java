package org.stelios.courses.ddd.branches.repositories;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "physicalBranches")
@Getter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class PhysicalBranchEntity extends BranchEntity {

    protected String address;

    public PhysicalBranchEntity(String id, String description, String owner, String city, String address) {
        super(id, description, owner, city);
        this.address = address;
    }
}
