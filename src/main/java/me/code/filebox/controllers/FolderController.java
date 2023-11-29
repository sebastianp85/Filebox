package me.code.filebox.controllers;

import me.code.filebox.exceptions.InvalidAuthException;
import me.code.filebox.exceptions.InvalidFolderNameException;
import me.code.filebox.models.Folder;
import me.code.filebox.services.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling folder-related operations.
 */
@RestController
public class FolderController {
    private final FolderService folderService;

    /**
     * Constructor for FolderController.
     *
     * @param folderService The service responsible for folder-related operations.
     */
    @Autowired
    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    /**
     * Handles folder creation request.
     *
     * @param token      Authorization token for the user.
     * @param folderName The name of the folder to be created.
     * @param username   The username of the user creating the folder.
     * @return ResponseEntity containing the newly created Folder object.
     * @throws InvalidAuthException       If the provided token is invalid.
     * @throws InvalidFolderNameException If the provided folder name is invalid.
     */
    @PostMapping("/add-folder")
    public ResponseEntity<Folder> createFolder(
            @RequestHeader("Authorization") String token,
            @RequestParam String folderName,
            @RequestParam String username)
            throws InvalidAuthException, InvalidFolderNameException {
        Folder newFolder = folderService.createFolder(folderName, username, token);
        return new ResponseEntity<>(newFolder, HttpStatus.CREATED);
    }
}