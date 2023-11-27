package me.code.filebox.dtos;

/**
 * Data transfer object (DTO) representing user information for registration and login.
 * Utilizes a record to automatically generate constructor, getters, and other methods.
 *
 * @param username The username of the user.
 * @param password The password associated with the user.
 */
public record UserDto(String username, String password) {
}
