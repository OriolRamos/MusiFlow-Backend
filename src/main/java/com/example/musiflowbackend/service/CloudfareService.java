package com.example.musiflowbackend.service;


import com.example.musiflowbackend.config.MinioConfig;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class CloudfareService {
    private final MinioClient minioClient;

    @Value("${cloudflare.r2.bucketName}")
    private String bucketName;

    @Autowired
    public CloudfareService(MinioClient minioClient){
        this.minioClient = minioClient;
    }

    public void uploadMusicFile(MultipartFile file){
        try{
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(file.getOriginalFilename())
                            .stream(file.getInputStream(), file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());
            System.out.println("Arxiu pujat correctament.");
        } catch (MinioException | IOException  | NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al pujar l'arxiu a R2", e);
        }
    }


    public InputStream getMusicFileStream(String filename) {
        try {
            return minioClient.getObject(
                    GetObjectArgs.builder()
                            .bucket(bucketName)
                            .object(filename)
                            .build());
        } catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            throw new RuntimeException("Error al obtenir l'arxiu de música desde R2", e);
        }
    }
}
