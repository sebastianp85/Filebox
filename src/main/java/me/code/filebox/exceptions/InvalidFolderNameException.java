package me.code.filebox.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception indicating that the provided folder name is invalid.
 * The exception is annotated with @ResponseStatus to specify the HTTP status code.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidFolderNameException extends Exception {

    /**
     * Constructor for InvalidFolderNameException.
     *
     * @param message A message indicating the details of the exception.
     */
    public InvalidFolderNameException(String message) {
        super(message);
    }
}
