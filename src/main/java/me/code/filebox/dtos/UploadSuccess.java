package me.code.filebox.dtos;

import lombok.Getter;

@Getter
public class UploadSuccess extends Success{

    public UploadSuccess(String message) {
        super(message);
    }
}
