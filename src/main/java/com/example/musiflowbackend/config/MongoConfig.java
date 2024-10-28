package com.example.musiflowbackend.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoConfig {

    private static final String CONNECTION_STRING = "mongodb+srv://usuari:MongoDB@musiflow.i6m9z.mongodb.net/mp3Database?retryWrites=true&w=majority&appName=MusiFlow";

    @Bean
    public MongoClient mongoClient() {
        // Configura el client de MongoDB amb les opcions de connexió
        ServerApi serverApi = ServerApi.builder()
                .version(ServerApiVersion.V1)
                .build();

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(CONNECTION_STRING))
                .serverApi(serverApi)
                .build();

        return MongoClients.create(settings);
    }
}
