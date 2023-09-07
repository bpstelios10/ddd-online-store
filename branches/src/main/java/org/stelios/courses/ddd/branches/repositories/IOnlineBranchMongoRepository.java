package org.stelios.courses.ddd.branches.repositories;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(value = "service.database.embedded", havingValue = "false")
//TODO change ID field
public interface IOnlineBranchMongoRepository extends MongoRepository<OnlineBranchEntity, String>, IOnlineBranchRepository {
}
