package org.stelios.courses.ddd.products.repositories;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@ConditionalOnProperty(value = "service.database.embedded", havingValue = "false")
public interface IProductMongoRepository extends MongoRepository<SouvenirEntity, String>, IProductRepository {
}
