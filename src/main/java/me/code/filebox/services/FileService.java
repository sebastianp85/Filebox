package me.code.filebox.services;

import me.code.filebox.dtos.DeleteSuccess;
import me.code.filebox.dtos.UploadSuccess;
import me.code.filebox.exceptions.FileDoesNotExistException;
import me.code.filebox.exceptions.InvalidAuthException;
import me.code.filebox.exceptions.InvalidFolderNameException;
import me.code.filebox.models.FileEntity;
import me.code.filebox.models.Folder;
import me.code.filebox.models.User;
import me.code.filebox.repositories.FileRepository;
import me.code.filebox.repositories.FolderRepository;
import me.code.filebox.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

/**
 * Service class for managing file-related operations.
 */
@Service
public class FileService {

    private final FolderRepository folderRepository;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final FileRepository fileRepository;
    private final FolderService folderService;

    @Autowired
    public FileService(FolderRepository folderRepository, UserService userService,
                       JwtTokenProvider jwtTokenProvider, FileRepository fileRepository, FolderService folderService) {
        this.folderRepository = folderRepository;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.fileRepository = fileRepository;
        this.folderService = folderService;
    }

    /**
     * Uploads a file to the specified folder for the given user.
     *
     * @param folderName The name of the folder to which the file will be uploaded.
     * @param username   The username of the user performing the upload.
     * @param token      The JWT token for authorization.
     * @param file       The file to be uploaded.
     * @return An UploadSuccess DTO indicating a successful upload.
     * @throws InvalidFolderNameException If the folder name is invalid.
     * @throws InvalidAuthException       If the authorization is invalid.
     * @throws IOException                If an I/O error occurs during file processing.
     */
    public UploadSuccess uploadFile(String folderName, String username, String token, MultipartFile file)
            throws InvalidFolderNameException, InvalidAuthException, IOException {
        validateAuthorization(username, token);
        User user = userService.findUserByUsername(username);
        Folder folder = folderService.getFolder(folderName, user);

        FileEntity newFile = new FileEntity(file, folder);
        folder.getFiles().add(newFile);
        folderRepository.save(folder);

        int fileId = newFile.getId();

        return new UploadSuccess("File uploaded successfully with id " + fileId);
    }

    /**
     * Deletes a file with the specified ID for the given user.
     *
     * @param username The username of the user performing the deletion.
     * @param token    The JWT token for authorization.
     * @param fileId   The ID of the file to be deleted.
     * @return A DeleteSuccess DTO indicating a successful deletion.
     * @throws InvalidAuthException       If the authorization is invalid.
     * @throws FileDoesNotExistException If the specified file does not exist.
     */
    public DeleteSuccess deleteFile(String username, String token, int fileId)
            throws InvalidAuthException, FileDoesNotExistException {
        validateAuthorization(username, token);
        FileEntity fileToDelete = getFilById(fileId, username);
        fileRepository.delete(fileToDelete);
        return new DeleteSuccess("File with id: " + fileId + " successfully deleted");
    }

    /**
     * Downloads a file with the specified ID for the given user.
     *
     * @param username The username of the user performing the download.
     * @param token    The JWT token for authorization.
     * @param fileId   The ID of the file to be downloaded.
     * @return A ResponseEntity containing the file content for download.
     * @throws InvalidAuthException       If the authorization is invalid.
     * @throws FileDoesNotExistException If the specified file does not exist.
     */
    public ResponseEntity<byte[]> downloadFile(String username, String token, int fileId)
            throws InvalidAuthException, FileDoesNotExistException {
        validateAuthorization(username, token);
        FileEntity fileToDownload = getFilById(fileId, username);

        byte[] fileContent = fileToDownload.getData();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("inline", fileToDownload.getFileName());

        return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
    }

    /**
     * Validates the authorization of a user based on the provided username and JWT token.
     *
     * @param username The username of the user.
     * @param token    The JWT token for authorization.
     * @throws InvalidAuthException If the authorization is invalid.
     */
    protected void validateAuthorization(String username, String token) throws InvalidAuthException {
        String tokenUsername = jwtTokenProvider.getUsernameFromToken(token);

        if (tokenUsername == null || !tokenUsername.equals(username)) {
            throw new InvalidAuthException("You are not authorized for that request");
        }
    }

    /**
     * Retrieves a FileEntity by its ID for the given user.
     *
     * @param fileId   The ID of the file to retrieve.
     * @param username The username of the user.
     * @return The FileEntity if found.
     * @throws FileDoesNotExistException If the specified file does not exist.
     * @throws InvalidAuthException       If the authorization is invalid.
     */
    private FileEntity getFilById(int fileId, String username) throws FileDoesNotExistException {
        Optional<FileEntity> fileOptional = fileRepository.findById(fileId);

        if (fileOptional.isPresent()) {
            FileEntity file = fileOptional.get();

            if (!file.getFolder().getUser().getUsername().equals(username)) {
                throw new InvalidAuthException("You are not authorized to download this file");
            }

            return file;
        } else {
            throw new FileDoesNotExistException("File not found");
        }
    }
}