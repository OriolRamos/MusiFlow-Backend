package com.example.musiflowbackend.controller;

import com.example.musiflowbackend.service.CloudfareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@RestController
@RequestMapping("/api/music")
public class MusicController {

    private final CloudfareService cloudfareService;

    @Autowired
    public MusicController(CloudfareService cloudfareService){
        this.cloudfareService = cloudfareService;
    }

    // Puedes crear un endpoint para subir el archivo de música
    @PostMapping("/upload")
    public ResponseEntity<String> uploadMusic(@RequestParam("file") MultipartFile file) {
        try{
            cloudfareService.uploadMusicFile(file);
            return ResponseEntity.ok("Arxiu pujat correctament");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al pujar l'arxiu");
        }
    }

    @GetMapping("stream/{filename}")
    public ResponseEntity<byte[]> streamMusic(@PathVariable String filename) {
        try {
            // Recupera el flujo de música desde el servicio
            InputStream musicStream = cloudfareService.getMusicFileStream(filename);

            // Verifica si el archivo fue encontrado
            if (musicStream == null) {
                throw new Exception("El archivo no fue encontrado.");
            }

            // Imprime un mensaje en la consola indicando que el archivo se ha recuperado correctamente
            System.out.println("Archivo " + filename + " recuperado exitosamente.");

            // Retorna la respuesta con el archivo
            byte[] musicBytes = musicStream.readAllBytes();
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .body(musicBytes);
        } catch (Exception e) {
            // Si ocurre un error, imprime el error en la consola
            System.out.println("Error al intentar recuperar el archivo: " + e.getMessage());

            // Retorna un error en la respuesta HTTP
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(("Error al recuperar l'arxiu: " + e.getMessage()).getBytes());
        }
    }
}
