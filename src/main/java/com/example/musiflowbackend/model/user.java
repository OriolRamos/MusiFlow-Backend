package com.example.musiflowbackend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Document(collection = "user")
public class user {

    @Id
    private String id;

    private String userName;

    private String password;

    private List<Mp3File> songs;

    // Constructor per defecte
    public user() {
        this.id = UUID.randomUUID().toString();
        this.songs = new ArrayList<>();
    }

    // Getters i Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Mp3File> getSongs() {
        return songs;
    }

    public void setSongs(List<Mp3File> songs) {
        this.songs = songs;
    }

    // Mètode per afegir una cançó a la llista
    public void addSong(Mp3File song) {
        this.songs.add(song);
    }
}
