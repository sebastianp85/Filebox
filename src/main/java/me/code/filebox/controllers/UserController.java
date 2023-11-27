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

/**
 * Controller class for handling user-related operations such as registration and login.
 */
@RestController
public class UserController {

    private final UserService userService;

    /**
     * Constructor for UserController.
     *
     * @param userService The service responsible for user-related operations.
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Handles user registration request.
     *
     * @param userDto Dto containing user registration information.
     * @return ResponseEntity containing RegistrationSuccess DTO.
     * @throws RegistrationFailureException If the registration process fails.
     */
    @PostMapping("/register")
    public ResponseEntity<RegistrationSuccess> registerUser(@RequestBody UserDto userDto) throws RegistrationFailureException {
        var result = userService.register(userDto.username(), userDto.password());
        return ResponseEntity.ok().body(result);
    }

    /**
     * Handles user login request.
     *
     * @param userDto Dto containing user login information.
     * @return ResponseEntity containing LoginSuccess DTO.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginSuccess> login(@RequestBody UserDto userDto) {
        var result = userService.login(userDto.username(), userDto.password());
        return ResponseEntity.ok().body(result);
    }
}
