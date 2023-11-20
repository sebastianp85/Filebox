package me.code.filebox.services;

import me.code.filebox.dtos.RegistrationSuccess;
import me.code.filebox.exceptions.RegistrationFailureException;
import me.code.filebox.models.User;
import me.code.filebox.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public RegistrationSuccess register(String username, String password) throws RegistrationFailureException {
        User newUser = new User (username, password);
        try {
            userRepository.save(newUser);
            return new RegistrationSuccess("User successfully registered with username: " + username);

        } catch (Exception e) {
            throw new RegistrationFailureException("Registration failed, username: '" + username + "' already exists in database");
        }
    }
}
