package me.code.filebox.services;

import me.code.filebox.exceptions.InvalidAuthException;
import me.code.filebox.exceptions.InvalidFolderNameException;
import me.code.filebox.models.Folder;
import me.code.filebox.models.User;
import me.code.filebox.repositories.FolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * Service class for managing folder-related operations.
 */
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

    /**
     * Creates a new folder with the specified name for the given user.
     *
     * @param folderName The name of the folder to be created.
     * @param username   The username of the user creating the folder.
     * @param token      The JWT token for authorization.
     * @return The created folder.
     * @throws InvalidFolderNameException If the folder name is invalid or already exists.
     * @throws InvalidAuthException       If the authorization is invalid.
     */
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

    /**
     * Retrieves a folder with the specified name for the given user.
     *
     * @param folderName The name of the folder to retrieve.
     * @param user       The user associated with the folder.
     * @return The retrieved folder.
     * @throws InvalidFolderNameException If the folder name is invalid or the folder does not exist.
     */
    public Folder getFolder(String folderName, User user) throws InvalidFolderNameException {
        return folderRepository.findByFolderNameAndUser(folderName, user)
                .orElseThrow(() -> new InvalidFolderNameException("Folder not found"));
    }
}