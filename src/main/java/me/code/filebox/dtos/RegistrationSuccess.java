package me.code.filebox.dtos;

import lombok.Getter;

/**
 * Data transfer object (DTO) representing a successful user registration response.
 * Extends the base Success class and inherits its properties.
 */
@Getter
public class RegistrationSuccess extends Success {

    /**
     * Constructor for RegistrationSuccess.
     *
     * @param message A message indicating the success of the user registration operation.
     */
    public RegistrationSuccess(String message) {
        super(message);
    }
}
