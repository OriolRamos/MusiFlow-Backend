package com.example.musiflowbackend.controller;

import com.example.musiflowbackend.model.Mp3File;
import com.example.musiflowbackend.model.user;
import com.example.musiflowbackend.repository.Mp3FileRepository;
import com.example.musiflowbackend.service.Mp3FileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.musiflowbackend.repository.userRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mp3files")
public class Mp3FileController {

    @Autowired
    private Mp3FileService mp3FileService;

    @Autowired
    private userRepository userRepository;  // Assegura't que userRepository està injectat correctament

    @Autowired
    private Mp3FileRepository mp3FileRepository;  // Assegura't que userRepository està injectat correctament


    private static final Logger logger = LoggerFactory.getLogger(Mp3FileController.class);


    // Endpoint to get all files
    /*@GetMapping("/username/{userName}")
    public ResponseEntity<List<Mp3File>> getAllFiles() {
        List<Mp3File> files = mp3FileService.getAllFiles();
        return new ResponseEntity<>(files, HttpStatus.OK);
    }*/

    @GetMapping("/users/{userName}")
    public ResponseEntity<user> getUserByUserName(@PathVariable("userName") String userName) {
        Optional<user> user = mp3FileService.findByUserName(userName);

        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Endpoint to upload a new file
    @PostMapping("/upload")
    public ResponseEntity<Mp3File> uploadFile(@RequestParam("file") MultipartFile file,
                                              @RequestParam(value = "title") String title,
                                              @RequestParam(value = "artist", required = false) String artist,
                                              @RequestParam(value = "album", required = false) String album,
                                              @RequestParam(value = "year", required = false) Integer year,
                                              @RequestParam(value = "genre", required = false) String genre,
                                              @RequestParam(value = "user", required = false) String json) throws JsonProcessingException {

        // Convertir el JSON de l'usuari a un objecte user
        ObjectMapper objectMapper = new ObjectMapper();
        user user = objectMapper.readValue(json, user.class);

        logger.debug("Usuari: " + user.getUserName() + ", Nombre de cançons: " + user.getSongs().size());

        // Crear el fitxer MP3
        Mp3File mp3File = mp3FileService.createMp3File(file, title, artist, album, year, genre, user);

        if (mp3File != null) {
            // Buscar l'usuari en la base de dades
            Optional<user> optionalUser = userRepository.findById(user.getId());
            if (optionalUser.isPresent()) {
                user existingUser = optionalUser.get();

                // Afegir la cançó a la llista de cançons de l'usuari
                existingUser.addSong(mp3File); // Utilitzem el mètode addSong() per afegir la cançó

                // Actualitzar l'usuari en la base de dades
                userRepository.save(existingUser);

                // Retornar la resposta amb l'objecte Mp3File creat
                return new ResponseEntity<>(mp3File, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Usuari no trobat
            }
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // Error en la creació del fitxer MP3
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable("id") String id) {
        // Buscar el fitxer MP3 a la base de dades
        Optional<Mp3File> mp3FileOptional = mp3FileRepository.findById(id);

        if (mp3FileOptional.isPresent()) {
            Mp3File mp3File = mp3FileOptional.get();

            // Buscar l'usuari per ID
            /*
            Optional<user> optionalUser = userRepository.findById(userId);

            if (optionalUser.isPresent()) {
                user existingUser = optionalUser.get();

                // Eliminar la cançó de la llista de cançons de l'usuari
                existingUser.getSongs().removeIf(song -> song.getId().equals(id));

                // Guardar l'usuari amb la llista actualitzada
                userRepository.save(existingUser);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Usuari no trobat
            }*/

            // Eliminar el fitxer MP3 de la base de dades
            mp3FileRepository.delete(mp3File);

            return ResponseEntity.ok().build(); // Retornar resposta d'èxit
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Fitxer no trobat
        }
    }




}
