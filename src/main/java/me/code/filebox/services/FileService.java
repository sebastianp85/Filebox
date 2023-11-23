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

    @Autowired
    public FileService(FolderRepository folderRepository, UserService userService, JwtTokenProvider jwtTokenProvider, FileRepository fileRepository) {
        this.folderRepository = folderRepository;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.fileRepository = fileRepository;
    }

    public UploadSuccess uploadFile(String folderName, String username, String token, MultipartFile file)
            throws InvalidFolderNameException, InvalidAuthException, IOException {
        String tokenUsername = jwtTokenProvider.getUsernameFromToken(token);

        if (tokenUsername != null && tokenUsername.equals(username)) {
            User user = userService.findUserByUsername(username);
            Folder folder = folderRepository.findByFolderNameAndUser(folderName, user)
                    .orElseThrow(() -> new InvalidFolderNameException("Folder not found"));

            FileEntity newFile = new FileEntity(file, folder);
            folder.getFiles().add(newFile);
            folderRepository.save(folder);

            return new UploadSuccess("File uploaded successfully");
        } else {
            throw new InvalidAuthException("You are not authorized for that request");
        }
    }

    public DeleteSuccess deleteFile(String username, String token, int fileId)
            throws InvalidAuthException, FileDoesNotExistException {
        String tokenUsername = jwtTokenProvider.getUsernameFromToken(token);

        if (tokenUsername != null && tokenUsername.equals(username)) {
            Optional<FileEntity> fileOptional = fileRepository.findById(fileId);
            if (fileOptional.isPresent()) {
                FileEntity fileToDelete = fileOptional.get();

                if (fileToDelete.getFolder().getUser().getUsername().equals(username)) {
                    fileRepository.delete(fileToDelete);
                    return new DeleteSuccess("File deleted successfully");
                } else {
                    throw new InvalidAuthException("You are not authorized to delete this file");
                }
            } else {
                throw new FileDoesNotExistException("File not found");
            }
        } else {
            throw new InvalidAuthException("You are not authorized for that request");
        }
    }
}