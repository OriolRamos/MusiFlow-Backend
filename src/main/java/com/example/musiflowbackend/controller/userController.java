    package com.example.musiflowbackend.controller;

    import com.example.musiflowbackend.model.Mp3File;
    import org.apache.catalina.User;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.HttpStatusCode;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import com.example.musiflowbackend.service.userService;
    import com.example.musiflowbackend.model.user;

    import java.util.List;

    @RestController
    @RequestMapping("/api/user")
    public class userController {

        @Autowired
        private userService userService;

        @PostMapping("/createUser")
        public ResponseEntity<user> createUser(@RequestParam String userName,
                                               @RequestParam String password){

            user newUser = userService.createUser(userName, password);

            if (newUser != null) {
                return ResponseEntity.ok(newUser);
            } else {
                return ResponseEntity.badRequest().build();
            }
        }

        @GetMapping("/findUser")
        public ResponseEntity<user> findUser(@RequestParam String userName,
                                             @RequestParam String password){

            try{
                user user = userService.findUser(userName, password);
                return ResponseEntity.ok(user);
            } catch (IllegalArgumentException e){
                return ResponseEntity.badRequest().body(null);
            }
        }

        @GetMapping("/{userName}/songs")
        public ResponseEntity<List<Mp3File>> getAllSongs(@PathVariable String userName){
            List<Mp3File> songs = userService.getAllSongs(userName);
            return new ResponseEntity<>(songs, HttpStatus.OK);
        }

    }





