package com.example.musiflowbackend.controller;

import com.example.musiflowbackend.model.Mp3File;
import com.example.musiflowbackend.repository.Mp3FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mp3files")
public class Mp3FileController {

    @Autowired
    private Mp3FileRepository mp3FileRepository;

    // Obtenir tots els fitxers
    @GetMapping
    public ResponseEntity<List<Mp3File>> getAllFiles() {
        List<Mp3File> files = mp3FileRepository.findAll();
        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    // Penjar un nou fitxer
    @PostMapping
    public ResponseEntity<Mp3File> uploadFile(@RequestBody Mp3File newFile) {
        Mp3File savedFile = mp3FileRepository.save(newFile);
        return new ResponseEntity<>(savedFile, HttpStatus.CREATED);
    }
}
