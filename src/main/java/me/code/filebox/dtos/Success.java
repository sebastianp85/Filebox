package me.code.filebox.dtos;

import lombok.Getter;

/**
 * Base class for representing success responses.
 * Provides a getter for the success message.
 */
@Getter
public class Success {

    // The message indicating the success of an operation.
    private String message;

    /**
     * Constructor for Success.
     *
     * @param message A message indicating the success of an operation.
     */
    public Success(String message) {
        this.message = message;
    }
}
