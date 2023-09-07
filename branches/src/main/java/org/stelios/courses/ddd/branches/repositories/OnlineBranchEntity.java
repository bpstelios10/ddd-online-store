package org.stelios.courses.ddd.branches.repositories;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "onlineBranches")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class OnlineBranchEntity extends BranchEntity {

    public OnlineBranchEntity(String id, String description, String owner, String city) {
        super(id, description, owner, city);
    }
}
