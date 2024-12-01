package com.example.Clubooks.logs.repository;

import com.example.Clubooks.logs.model.LogUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableMongoRepositories
public interface LogsRepository extends MongoRepository<LogUser, String> {
}
