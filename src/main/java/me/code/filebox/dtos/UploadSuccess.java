package me.code.filebox.dtos;

import lombok.Getter;

/**
 * Data transfer object (DTO) representing a successful file upload response.
 * Extends the base Success class and inherits its properties.
 */
@Getter
public class UploadSuccess extends Success {

    /**
     * Constructor for UploadSuccess.
     *
     * @param message A message indicating the success of the file upload operation.
     */
    public UploadSuccess(String message) {
        super(message);
    }
}
