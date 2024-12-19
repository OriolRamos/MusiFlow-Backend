package com.example.musiflowbackend.controller;

import com.example.musiflowbackend.model.Mp3File;
import com.example.musiflowbackend.model.user;
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

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/mp3files")
public class Mp3FileController {

    @Autowired
    private Mp3FileService mp3FileService;


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

        ObjectMapper objectMapper = new ObjectMapper();
        user user = objectMapper.readValue(json, user.class);

        logger.debug(String.valueOf(user.getSongs().size()));
        Mp3File mp3File = mp3FileService.createMp3File(file, title, artist, album, year, genre, user);

        if (mp3File != null) {
            return new ResponseEntity<>(mp3File, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable("id") String id, @PathVariable("user") String user) {
        mp3FileService.deleteMp3File(id, user);
        return ResponseEntity.ok().build();
    }


}
