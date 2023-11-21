package me.code.filebox.controllers;

import me.code.filebox.dtos.CreateFolderSuccess;
import me.code.filebox.exceptions.InvalidAuthException;
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
    public ResponseEntity<CreateFolderSuccess> createFolder(
            @RequestHeader("Authorization") String token,
            @RequestParam String folderName,
            @RequestParam String username)
    throws InvalidAuthException {
        boolean isValid = jwtTokenProvider.validate(token);
        if(isValid) {
            Folder newFolder = folderService.createFolder(folderName, username);
            return new ResponseEntity<>(new CreateFolderSuccess("A new folder has been created with the name '" + folderName + "', and it is associated with the user '" + username + "'."), HttpStatus.CREATED);
        } else {
            throw new InvalidAuthException("Access denied");
        }
    }
/*
    @PostMapping("/login")
    public ResponseEntity<LoginSuccess> login(@RequestBody UserDto userDto) {
        var result = userService.login(userDto.username(), userDto.password());
        return ResponseEntity.ok().body(result);
    }
 */

}
