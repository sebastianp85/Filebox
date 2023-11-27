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

@RestController
public class FolderController {
    private final FolderService folderService;

    @Autowired
    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

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
