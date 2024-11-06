package com.example.musiflowbackend.service;

import com.example.musiflowbackend.model.Mp3File;
import com.example.musiflowbackend.repository.Mp3FileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class Mp3FileService {

    private static final Logger log = LoggerFactory.getLogger(Mp3FileService.class);
    @Autowired
    private Mp3FileRepository mp3FileRepository;

    public List<Mp3File> getAllFiles() {
        return mp3FileRepository.findAll();
    }

    public Mp3File createMp3File(MultipartFile file, String title, String artist, String album, Integer year, String genre) {
        Mp3File mp3File = new Mp3File();

        mp3File.setTitle(title);
        mp3File.setArtist(artist);
        mp3File.setAlbum(album);
        mp3File.setYear(year != null ? year : 0);
        mp3File.setGenre(genre);

        return mp3FileRepository.save(mp3File);

    }
}
