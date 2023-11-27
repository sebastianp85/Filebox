package me.code.filebox.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception indicating that authentication is invalid.
 * The exception is annotated with @ResponseStatus to specify the HTTP status code.
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class InvalidAuthException extends RuntimeException {

    /**
     * Constructor for InvalidAuthException.
     *
     * @param message A message indicating the details of the exception.
     */
    public InvalidAuthException(String message) {
        super(message);
    }
}
