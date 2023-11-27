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

    public RegistrationSuccess register(String username, String password) throws RegistrationFailureException {
        User newUser = new User(username, password);
        try {
            userRepository.save(newUser);
            return new RegistrationSuccess("User successfully registered with username: " + username);

        } catch (Exception e) {
            throw new RegistrationFailureException("Registration failed, username: '" + username + "' already exists in database");
        }
    }

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

    public User findUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElse(null);
    }
}
