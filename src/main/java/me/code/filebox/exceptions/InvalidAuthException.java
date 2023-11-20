package me.code.filebox.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class InvalidAuthException extends RuntimeException{
    public InvalidAuthException(String message) {
        super(message);
    }
}
