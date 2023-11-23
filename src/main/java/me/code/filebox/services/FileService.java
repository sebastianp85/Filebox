
package me.code.filebox.services;


import me.code.filebox.dtos.UploadSuccess;
import me.code.filebox.exceptions.InvalidAuthException;
import me.code.filebox.exceptions.InvalidFolderNameException;
import me.code.filebox.models.FileEntity;
import me.code.filebox.models.Folder;
import me.code.filebox.models.User;
import me.code.filebox.repositories.FolderRepository;
import me.code.filebox.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class FileService {
    private final FolderRepository folderRepository;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public FileService(FolderRepository folderRepository, UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.folderRepository = folderRepository;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

/*
    public UploadSuccess uploadFile(String folderName, MultipartFile file,  int userId)
            throws InvalidFolderNameException, InvalidAuthException, IOException {
        try {
            Folder folder = folderRepository.findByFolderNameAndUser(file, userId);
        }


 */

        public UploadSuccess uploadFile (String folderName, String username, String token, MultipartFile file)
            throws InvalidFolderNameException, InvalidAuthException, IOException {
            String tokenUsername = jwtTokenProvider.getUsernameFromToken(token);

            if (tokenUsername != null && tokenUsername.equals(username)) {
                User user = userService.findUserByUsername(username);
                Folder folder = folderRepository.findByFolderNameAndUser(folderName, user)
                        .orElseThrow(() -> new InvalidFolderNameException("Folder not found"));

                //byte[] fileContent = file.getBytes();
                FileEntity newFile = new FileEntity(file, folder);
                folder.getFiles().add(newFile);
                folderRepository.save(folder);

                return new UploadSuccess("File uploaded successfully");
            } else {
                throw new InvalidAuthException("You are not authorized for that request");
            }
        }
    }


/*
public FileEntity uploadFile (String folderName, String username, String token, MultipartFile file)
            throws InvalidFolderNameException, InvalidAuthException, IOException {
            String tokenUsername = jwtTokenProvider.getUsernameFromToken(token);

            if (tokenUsername != null && tokenUsername.equals(username)) {
                User user = userService.findUserByUsername(username);
                Folder folder = folderRepository.findByFolderNameAndUser(folderName, user)
                        .orElseThrow(() -> new InvalidFolderNameException("Folder not found"));

                //byte[] fileContent = file.getBytes();
                FileEntity newFile = new FileEntity(file, folder);
                folder.getFiles().add(newFile);
                folderRepository.save(folder);

                return newFile;
            } else {
                throw new InvalidAuthException("You are not authorized for that request");
            }
        }
    }
 */


/*

 */