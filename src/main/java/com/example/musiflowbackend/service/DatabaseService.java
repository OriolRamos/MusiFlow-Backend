package com.example.musiflowbackend.service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;

@Service
public class DatabaseService {

    @Autowired
    private MongoClient mongoClient;

    @PostConstruct
    public void init() {
        try {
            // Comprova la connexió enviant una comanda "ping" a la base de dades "admin"
            MongoDatabase database = mongoClient.getDatabase("admin");
            database.runCommand(new Document("ping", 1));
            System.out.println("Connexió a MongoDB confirmada amb èxit.");
        } catch (Exception e) {
            System.err.println("Error en connectar amb MongoDB: " + e.getMessage());
            throw e;
        }
    }
}
