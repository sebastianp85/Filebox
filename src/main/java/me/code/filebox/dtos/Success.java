package me.code.filebox.dtos;

import lombok.Getter;

@Getter
public class Success {

    private String message;

    public Success(String message) {
        this.message = message;
    }
}
