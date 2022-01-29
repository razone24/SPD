package com.rbalasa.accountancyapp.controller;

import com.rbalasa.accountancyapp.model.User;
import com.rbalasa.accountancyapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/save")
    @CrossOrigin("http://localhost:9090")
    public ResponseEntity save(@RequestBody User user) {
        User storedUser = userService.store(user);
        if(storedUser != null) {
            return ResponseEntity.ok(storedUser);
        } else {
            return ResponseEntity.badRequest().body("BAD REQUEST!");
        }
    }

}
