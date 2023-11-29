package me.code.filebox.dtos;

import lombok.Getter;

/**
 * Data transfer object (DTO) representing a successful login response.
 * Extends the base Success class and inherits its properties.
 * Provides a getter for the authentication token.
 */
@Getter
public class LoginSuccess extends Success {

    // The authentication token associated with a successful login.
    private String token;

    /**
     * Constructor for LoginSuccess.
     *
     * @param message A message indicating the success of the login operation.
     * @param token   The authentication token generated upon successful login.
     */
    public LoginSuccess(String message, String token) {
        super(message);
        this.token = token;
    }
}
