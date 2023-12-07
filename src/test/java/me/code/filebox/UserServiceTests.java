package me.code.filebox;

import me.code.filebox.dtos.UserDto;
import me.code.filebox.exceptions.RegistrationFailureException;
import me.code.filebox.models.User;
import me.code.filebox.repositories.UserRepository;
import me.code.filebox.security.JwtTokenProvider;
import me.code.filebox.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

/**
 * Test for the business logic in UserService class.
 */
@SpringBootTest
public class UserServiceTests {

    @Autowired
    UserService userService;

    @MockBean
    UserRepository userRepository;

    @MockBean
    JwtTokenProvider jwtTokenProvider;

    /**
     * Test for the successful user registration functionality.
     */
    @Test
    void registerUserSuccess() {
        // given
        var username = "testUser";
        var password = "testPassword";
        var userDto = new UserDto(username, password);
        var registrationSuccessMessage = "User successfully registered with username: " + username;

        Mockito.when(userRepository.save(Mockito.any())).then(invocation -> {
            var newUser = (User) invocation.getArgument(0);
            return newUser;
        });

        // when
        var result = Assertions.assertDoesNotThrow(() -> userService.register(userDto.username(), userDto.password()));

        // then
        Assertions.assertEquals(registrationSuccessMessage, result.getMessage());
    }

    /**
     * Test for user registration failure when attempting to register an existing user.
     */
    @Test
    void registerUserFailure() {
        // given
        var username = "existingUser";
        var password = "existingPassword";
        var userDto = new UserDto(username, password);
        var existingUser = new User(username, password);

        Mockito.when(userRepository.save(Mockito.any())).thenThrow(DataIntegrityViolationException.class);
        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(existingUser));

        // when
        Assertions.assertThrows(RegistrationFailureException.class, () -> userService.register(userDto.username(), userDto.password()));
    }
}
