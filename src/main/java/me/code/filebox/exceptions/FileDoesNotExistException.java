package me.code.filebox.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception indicating that a requested file does not exist.
 * The exception is annotated with @ResponseStatus to specify the HTTP status code.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class FileDoesNotExistException extends RuntimeException {

    /**
     * Constructor for FileDoesNotExistException.
     *
     * @param message A message indicating the details of the exception.
     */
    public FileDoesNotExistException(String message) {
        super(message);
    }
}
