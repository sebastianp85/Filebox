package me.code.filebox.services;

import lombok.Getter;
import me.code.filebox.dtos.LoginSuccess;
import me.code.filebox.dtos.RegistrationSuccess;
import me.code.filebox.exceptions.InvalidAuthException;
import me.code.filebox.exceptions.RegistrationFailureException;
import me.code.filebox.models.User;
import me.code.filebox.repositories.UserRepository;
import me.code.filebox.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for managing user-related operations.
 */
@Service
@Getter
public class UserService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public UserService(UserRepository userRepository, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * Registers a new user with the provided username and password.
     *
     * @param username The username of the user to be registered.
     * @param password The password of the user to be registered.
     * @return A RegistrationSuccess DTO indicating a successful registration.
     * @throws RegistrationFailureException If registration fails, typically due to an existing username.
     */
    public RegistrationSuccess register(String username, String password) throws RegistrationFailureException {
        User newUser = new User(username, password);
        try {
            userRepository.save(newUser);
            return new RegistrationSuccess("User successfully registered with username: " + username);

        } catch (Exception e) {
            throw new RegistrationFailureException("Registration failed, username: '" + username + "' already exists in the database");
        }
    }

    /**
     * Logs in a user with the provided username and password.
     *
     * @param username The username of the user attempting to log in.
     * @param password The password of the user attempting to log in.
     * @return A LoginSuccess DTO indicating a successful login.
     * @throws InvalidAuthException If the login fails due to incorrect username or password.
     */
    public LoginSuccess login(String username, String password) throws InvalidAuthException {
        boolean isUserDetailsValid = userRepository.isValidUser(username, password);

        if (isUserDetailsValid) {
            User user = findUserByUsername(username);
            String token = jwtTokenProvider.generateToken(user);

            return new LoginSuccess("User successfully logged in ", token);

        } else {
            throw new InvalidAuthException("Authorization failed, incorrect username or password");
        }
    }

    /**
     * Finds a user by their username.
     *
     * @param username The username of the user to be retrieved.
     * @return The user if found, or null otherwise.
     */
    public User findUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElse(null);
    }
}