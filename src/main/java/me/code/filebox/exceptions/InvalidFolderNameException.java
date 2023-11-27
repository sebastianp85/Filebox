package me.code.filebox.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidFolderNameException extends Exception {
    public InvalidFolderNameException(String message) {
        super(message);
    }
}