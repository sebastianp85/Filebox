package me.code.filebox.controllers;

import me.code.filebox.dtos.DeleteSuccess;
import me.code.filebox.dtos.UploadSuccess;
import me.code.filebox.exceptions.FileDoesNotExistException;
import me.code.filebox.exceptions.InvalidAuthException;
import me.code.filebox.exceptions.InvalidFolderNameException;
import me.code.filebox.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/{username}/upload-file")
    public ResponseEntity<UploadSuccess> uploadFile(
            @RequestHeader("Authorization") String token,
            @RequestParam("file") MultipartFile file,
            @RequestParam("folderName") String folderName,
            @PathVariable("username") String username)
            throws InvalidAuthException, InvalidFolderNameException, IOException {
        return ResponseEntity.ok(fileService.uploadFile(folderName, username, token, file));
    }

    @DeleteMapping("/{username}/delete")
    public ResponseEntity<DeleteSuccess> deleteFile(
            @RequestHeader("Authorization") String token,
            @PathVariable("username") String username,
            @RequestParam("fileId") int fileId)
            throws InvalidAuthException, FileDoesNotExistException {
        return ResponseEntity.ok(fileService.deleteFile(username, token, fileId));
    }

    @GetMapping("/{username}/download")
    public ResponseEntity<byte[]> downloadFile(
            @RequestHeader("Authorization") String token,
            @PathVariable("username") String username,
            @RequestParam("file") int fileId)
            throws InvalidAuthException, FileDoesNotExistException {
        return fileService.downloadFile(username, token, fileId);
    }
}

