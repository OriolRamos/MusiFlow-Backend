package com.example.musiflowbackend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.musiflowbackend.repository.userRepository;
import com.example.musiflowbackend.model.user;

import java.util.List;
import java.util.Optional;

@Service
public class userService {


    @Autowired
    private userRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(userService.class);


    public user createUser(String userName, String password){
        user newUser = new user();

        newUser.setUserName(userName);
        newUser.setPassword(password);

        return userRepository.save(newUser);
    }

    public user findUser(String userName, String password){
        Optional<user> optionalUser = userRepository.findByUserName(userName);

        if(optionalUser.isEmpty()){
            throw new IllegalArgumentException("No existeix usuari");
        }

        user user = optionalUser.get();

        if(!user.getPassword().equals(password)){
            throw new IllegalArgumentException("Contrasenya incorrecta");
        }

        return  user;
    }

    public List<user> getAllUsers() {
        return userRepository.findAll();  // Este método debe devolver la lista de usuarios desde la base de datos
    }
}
