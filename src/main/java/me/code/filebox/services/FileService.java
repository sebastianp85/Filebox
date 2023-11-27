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

    public UploadSuccess uploadFile(String folderName, String username, String token, MultipartFile file)
            throws InvalidFolderNameException, InvalidAuthException, IOException {
        validateAuthorization(username, token);
        User user = userService.findUserByUsername(username);
        Folder folder = folderService.getFolder(folderName, user);

        FileEntity newFile = new FileEntity(file, folder);
        folder.getFiles().add(newFile);
        folderRepository.save(folder);

        return new UploadSuccess("File uploaded successfully");
    }

    public DeleteSuccess deleteFile(String username, String token, int fileId)
            throws InvalidAuthException, FileDoesNotExistException {
        validateAuthorization(username, token);
        FileEntity fileToDelete = getFilById(fileId, username);
        fileRepository.delete(fileToDelete);
        return new DeleteSuccess("File deleted successfully");
    }

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

    protected void validateAuthorization(String username, String token) throws InvalidAuthException {
        String tokenUsername = jwtTokenProvider.getUsernameFromToken(token);

        if (tokenUsername == null || !tokenUsername.equals(username)) {
            throw new InvalidAuthException("You are not authorized for that request");
        }
    }

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