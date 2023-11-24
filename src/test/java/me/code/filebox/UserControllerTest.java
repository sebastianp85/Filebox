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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void testRegisterUser() throws RegistrationFailureException {
        // Skapa ett UserDto-objekt för testet
        UserDto userDto = new UserDto("testuser", "testpassword");

        // Mocka UserService:s beteende
        when(userService.register("testuser", "testpassword"))
                .thenReturn(new RegistrationSuccess("User successfully registered with username: testuser"));

        // Kör testet
        ResponseEntity<RegistrationSuccess> responseEntity = userController.registerUser(userDto);

        // Kontrollera att anropet var framgångsrikt och returnerade rätt meddelande
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("User successfully registered with username: testuser", responseEntity.getBody().getMessage());
    }
}
