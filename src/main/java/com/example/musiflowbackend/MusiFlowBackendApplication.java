package com.example.musiflowbackend;

import com.example.musiflowbackend.model.Mp3File;
import com.example.musiflowbackend.repository.Mp3FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class MusiFlowBackendApplication implements CommandLineRunner {

    @Autowired
    private Mp3FileRepository mp3FileRepository;

    public static void main(String[] args) {
        SpringApplication.run(MusiFlowBackendApplication.class, args);
        System.out.println("Aplicació de backend iniciada amb èxit.");
    }

    @Override
    public void run(String... args) throws Exception {
        // Obtenim tots els fitxers MP3 de la base de dades
        List<Mp3File> files = mp3FileRepository.findAll();
        if (files.isEmpty()) {
            System.out.println("No hi ha fitxers MP3 disponibles a la base de dades.");
        } else {
            System.out.println("Fitxers MP3 disponibles a la base de dades:");
            files.forEach(file -> System.out.println(
                    "Títol: " + file.getTitle() + ", Artista: " + file.getArtist() +
                            ", Àlbum: " + file.getAlbum() + ", Any: " + file.getYear()
            ));
        }
    }
}
