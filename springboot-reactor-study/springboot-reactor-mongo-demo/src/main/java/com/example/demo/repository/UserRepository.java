package com.example.demo.repository;

import com.example.demo.data.User;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * @author caocs
 * @date 2021/10/1
 */
@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {
    Flux<User> findByAgeBetween(int start, int end);

    @Query("{'age':{'$gte':20,'$lte':30}}")
    Flux<User> oldUser();
}
