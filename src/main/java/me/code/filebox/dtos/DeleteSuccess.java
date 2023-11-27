package me.code.filebox.dtos;

/**
 * Represents a success response for delete operations.
 * Extends the base Success class and inherits its properties.
 */
public class DeleteSuccess extends Success {

    /**
     * Constructor for DeleteSuccess.
     *
     * @param message A message indicating the success of the delete operation.
     */
    public DeleteSuccess(String message) {
        super(message);
    }
}
