package org.stelios.courses.ddd.branches.repositories;

import java.util.List;
import java.util.Optional;

public interface IPhysicalBranchRepository {

    PhysicalBranchEntity save(PhysicalBranchEntity entity);

    List<PhysicalBranchEntity> findAll();

    Optional<PhysicalBranchEntity> findById(String id);
}
