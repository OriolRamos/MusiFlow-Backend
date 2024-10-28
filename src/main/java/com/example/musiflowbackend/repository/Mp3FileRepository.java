package com.example.musiflowbackend.repository;

import com.example.musiflowbackend.model.Mp3File;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Mp3FileRepository extends MongoRepository<Mp3File, String> {
}

