package com.example.musiflowbackend.repository;

import com.example.musiflowbackend.model.Mp3File;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface Mp3FileRepository extends MongoRepository<Mp3File, String> {

    // Mètode per obtenir un fitxer MP3 per ID (ja existeix a MongoRepository, però es pot declarar explícitament)
    Optional<Mp3File> findById(String id);
}
