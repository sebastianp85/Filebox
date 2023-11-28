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

/**
 * Controller class for handling file-related operations.
 */
@RestController
public class FileController {

    private final FileService fileService;

    /**
     * Constructor for FileController.
     *
     * @param fileService The service responsible for file-related operations.
     */
    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * Handles file upload request.
     *
     * @param token      Authorization token for user authentication.
     * @param file       The file to be uploaded.
     * @param folderName The name of the folder where the file should be uploaded.
     * @param username   The username of the user initiating the upload.
     * @return ResponseEntity containing UploadSuccess DTO.
     * @throws InvalidAuthException         If the provided token is invalid.
     * @throws InvalidFolderNameException   If the provided folder name is invalid.
     * @throws IOException                  If an I/O error occurs during file upload.
     */
    @PostMapping("/{username}/upload-file")
    public ResponseEntity<UploadSuccess> uploadFile(
            @RequestHeader("Authorization") String token,
            @RequestParam("file") MultipartFile file,
            @RequestParam("folderName") String folderName,
            @PathVariable("username") String username)
            throws InvalidAuthException, InvalidFolderNameException, IOException {
        return ResponseEntity.ok(fileService.uploadFile(folderName, username, token, file));
    }

    /**
     * Handles file deletion request.
     *
     * @param token    Authorization token for user authentication.
     * @param username The username of the user initiating the deletion.
     * @param fileId   The ID of the file to be deleted.
     * @return ResponseEntity containing DeleteSuccess DTO.
     * @throws InvalidAuthException       If the provided token is invalid.
     * @throws FileDoesNotExistException  If the specified file does not exist.
     */
    @DeleteMapping("/{username}/delete")
    public ResponseEntity<DeleteSuccess> deleteFile(
            @RequestHeader("Authorization") String token,
            @PathVariable("username") String username,
            @RequestParam("fileId") int fileId)
            throws InvalidAuthException, FileDoesNotExistException {
        return ResponseEntity.ok(fileService.deleteFile(username, token, fileId));
    }

    /**
     * Handles file download request.
     *
     * @param token    Authorization token for user.
     * @param username The username of the user initiating the download.
     * @param fileId   The ID of the file to be downloaded.
     * @return ResponseEntity containing the byte array of the file.
     * @throws InvalidAuthException       If the provided token is invalid.
     * @throws FileDoesNotExistException  If the specified file does not exist.
     */
    @GetMapping("/{username}/download")
    public ResponseEntity<byte[]> downloadFile(
            @RequestHeader("Authorization") String token,
            @PathVariable("username") String username,
            @RequestParam("fileId") int fileId)
            throws InvalidAuthException, FileDoesNotExistException {
        return fileService.downloadFile(username, token, fileId);
    }
}