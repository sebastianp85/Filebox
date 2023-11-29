package me.code.filebox;

import me.code.filebox.controllers.UserController;
import me.code.filebox.dtos.RegistrationSuccess;
import me.code.filebox.dtos.UserDto;
import me.code.filebox.exceptions.RegistrationFailureException;
import me.code.filebox.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void testRegisterUser() throws RegistrationFailureException {
        // Create a UserDto object for the test
        UserDto userDto = new UserDto("testuser", "testpassword");

        // Mock the behavior of UserService
        when(userService.register("testuser", "testpassword"))
                .thenReturn(new RegistrationSuccess("User successfully registered with username: testuser"));

        // Run the test
        ResponseEntity<RegistrationSuccess> responseEntity = userController.registerUser(userDto);

        // Verify that the call was successful and returned the correct message
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("User successfully registered with username: testuser", responseEntity.getBody().getMessage());
    }
}
