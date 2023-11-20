package me.code.filebox.dtos;

import lombok.Getter;

@Getter
public class RegistrationSuccess extends Success {

    public RegistrationSuccess(String message) {
        super(message);
    }
}
