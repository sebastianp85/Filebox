package me.code.filebox.controllers;

import me.code.filebox.exceptions.InvalidAuthException;
import me.code.filebox.exceptions.InvalidFolderNameException;
import me.code.filebox.models.Folder;
import me.code.filebox.security.JwtTokenProvider;
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
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public FolderController(FolderService folderService, JwtTokenProvider jwtTokenProvider) {
        this.folderService = folderService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/add-folder")
    public ResponseEntity<Folder> createFolder(
            @RequestHeader("Authorization") String token,
            @RequestParam String folderName,
            @RequestParam String username)
            throws InvalidAuthException, InvalidFolderNameException {
        boolean isValid = jwtTokenProvider.validate(token);
        if (isValid) {
            Folder newFolder = folderService.createFolder(folderName, username, token);
            return new ResponseEntity<>(newFolder, HttpStatus.CREATED);
        } else {
            throw new InvalidAuthException("Access denied");
        }
    }
}
