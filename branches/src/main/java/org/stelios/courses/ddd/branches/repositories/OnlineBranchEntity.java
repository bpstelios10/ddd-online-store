package org.stelios.courses.ddd.branches.repositories;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "onlineBranches")
@Getter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OnlineBranchEntity extends BranchEntity {

    public OnlineBranchEntity(String id, String description, String owner, String city) {
        super(id, description, owner, city);
    }
}
