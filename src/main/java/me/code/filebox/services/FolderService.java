package me.code.filebox.services;

import me.code.filebox.exceptions.InvalidAuthException;
import me.code.filebox.exceptions.InvalidFolderNameException;
import me.code.filebox.models.Folder;
import me.code.filebox.models.User;
import me.code.filebox.repositories.FolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class FolderService {
    private final FolderRepository folderRepository;
    private final UserService userService;

    @Autowired
    @Lazy
    private FileService fileService;

    @Autowired
    public FolderService(FolderRepository folderRepository, UserService userService) {
        this.folderRepository = folderRepository;
        this.userService = userService;
    }


    public Folder createFolder(String folderName, String username, String token)
            throws InvalidFolderNameException, InvalidAuthException {
        fileService.validateAuthorization(username, token);
        User user = userService.findUserByUsername(username);

        if (folderRepository.existsByFolderNameAndUser(folderName, user)) {
            throw new InvalidFolderNameException("A folder with the name '" + folderName + "' already exists for the user '" + username + "'");
        }

        Folder newFolder = new Folder(folderName, user);
        return folderRepository.save(newFolder);
    }

    protected Folder getFolder(String folderName, User user) throws InvalidFolderNameException {
        return folderRepository.findByFolderNameAndUser(folderName, user)
                .orElseThrow(() -> new InvalidFolderNameException("Folder not found"));
    }
}

