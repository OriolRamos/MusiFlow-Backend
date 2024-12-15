package com.example.musiflowbackend.controller;

import com.example.musiflowbackend.model.Mp3File;
import com.example.musiflowbackend.service.Mp3FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/mp3files")
public class Mp3FileController {

    @Autowired
    private Mp3FileService mp3FileService;

    // Endpoint to get all files
    @GetMapping
    public ResponseEntity<List<Mp3File>> getAllFiles() {
        List<Mp3File> files = mp3FileService.getAllFiles();
        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    // Endpoint to upload a new file
    @PostMapping("/upload")
    public ResponseEntity<Mp3File> uploadFile(@RequestParam("file") MultipartFile file,
                                              @RequestParam(value = "title", required = false) String title,
                                              @RequestParam(value = "artist", required = false) String artist,
                                              @RequestParam(value = "album", required = false) String album,
                                              @RequestParam(value = "year", required = false) Integer year,
                                              @RequestParam(value = "genre", required = false) String genre) {
        Mp3File mp3File = mp3FileService.createMp3File(file, title, artist, album, year, genre);

        if (mp3File != null) {
            return new ResponseEntity<>(mp3File, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
