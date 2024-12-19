package com.example.musiflowbackend.repository;

import com.example.musiflowbackend.model.user;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface userRepository extends MongoRepository<user, String> {

    // Busca un usuari pel seu nom d'usuari
    Optional<user> findByUserName(String userName);
}
