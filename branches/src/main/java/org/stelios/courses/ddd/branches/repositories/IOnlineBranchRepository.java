package org.stelios.courses.ddd.branches.repositories;

import java.util.List;
import java.util.Optional;

public interface IOnlineBranchRepository {

    OnlineBranchEntity save(OnlineBranchEntity entity);

    List<OnlineBranchEntity> findAll();

    Optional<OnlineBranchEntity> findById(String id);
}
