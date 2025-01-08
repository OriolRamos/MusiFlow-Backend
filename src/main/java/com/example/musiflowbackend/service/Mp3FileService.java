package com.example.musiflowbackend.service;

import com.example.musiflowbackend.model.Mp3File;
import com.example.musiflowbackend.model.user;
import com.example.musiflowbackend.repository.Mp3FileRepository;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.example.musiflowbackend.repository.userRepository;

import java.util.Optional;

@Service
public class Mp3FileService {

    private static final Logger log = LoggerFactory.getLogger(Mp3FileService.class);
    @Autowired
    private Mp3FileRepository mp3FileRepository;

    @Autowired
    private userRepository userRepository;

    public Optional<user> findByUserName(String user) {

        return userRepository.findByUserName(user);
    }

    public Mp3File createMp3File(MultipartFile file, String title, String artist, String album, Integer year, String genre, user user) {
        Mp3File mp3File = new Mp3File();

        mp3File.setTitle(title);
        mp3File.setArtist(artist);
        mp3File.setAlbum(album);
        mp3File.setYear(year != null ? year : 0);
        mp3File.setGenre(genre);

        user existingUser = userRepository.findById(user.getId()).orElse(null);
        if (existingUser != null) {

            mp3FileRepository.save(mp3File);

            log.info("Creant fitxer MP3 amb les següents dades:");
            log.info("Id: " + mp3File.getId());
            log.info("Títol: " + mp3File.getTitle());
            log.info("Artista: " + mp3File.getArtist());
            log.info("Àlbum: " + mp3File.getAlbum());
            log.info("Any: " + mp3File.getYear());
            log.info("Gènere: " + mp3File.getGenre());

            existingUser.addSong(mp3File);

            userRepository.save(existingUser);

            return mp3File;
        }
     else {
        log.error("No existeix aquest usuari");
        return null;
    }

    }

    public void deleteMp3File(String id, user user) {
        // Buscar el fitxer a la base de dades
        Optional<Mp3File> mp3FileOptional = mp3FileRepository.findById(id);

        if (mp3FileOptional.isPresent()) {
            Mp3File mp3File = mp3FileOptional.get();

            // Eliminar el fitxer MP3 de la base de dades
            mp3FileRepository.delete(mp3File);
            log.info("Fitxer MP3 amb ID " + id + " eliminat de la base de dades.");

            // Eliminar el fitxer de la llista de cançons de l'usuari
            user.getSongs().removeIf(song -> song.getId().equals(id));
            userRepository.save(user); // Guardar l'usuari amb la llista actualitzada
            log.info("Fitxer MP3 amb ID " + id + " eliminat de la llista de l'usuari.");
        } else {
            log.error("No s'ha trobat el fitxer MP3 amb ID " + id);
        }
    }
}
