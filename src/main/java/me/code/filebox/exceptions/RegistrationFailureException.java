package me.code.filebox.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception indicating that user registration has failed.
 * The exception is annotated with @ResponseStatus to specify the HTTP status code.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RegistrationFailureException extends Exception {

    /**
     * Constructor for RegistrationFailureException.
     *
     * @param message A message indicating the details of the exception.
     */
    public RegistrationFailureException(String message) {
        super(message);
    }
}
