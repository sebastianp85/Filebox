package me.code.filebox.controllers;

import me.code.filebox.dtos.LoginSuccess;
import me.code.filebox.dtos.RegistrationSuccess;
import me.code.filebox.dtos.UserDto;
import me.code.filebox.exceptions.RegistrationFailureException;
import me.code.filebox.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationSuccess> registerUser(@RequestBody UserDto userDto) throws RegistrationFailureException {
        var result = userService.register(userDto.username(), userDto.password());
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginSuccess> login(@RequestBody UserDto userDto) {
        var result = userService.login(userDto.username(), userDto.password());
        return ResponseEntity.ok().body(result);
    }
}
