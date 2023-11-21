package me.code.filebox.services;

import me.code.filebox.exceptions.InvalidAuthException;
import me.code.filebox.exceptions.InvalidFolderNameException;
import me.code.filebox.models.Folder;
import me.code.filebox.models.User;
import me.code.filebox.repositories.FolderRepository;
import me.code.filebox.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FolderService {
    private final FolderRepository folderRepository;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public FolderService(FolderRepository folderRepository, UserService userService, JwtTokenProvider jwtTokenProvider) {
        this.folderRepository = folderRepository;
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public Folder createFolder(String folderName, String username, String token) throws InvalidFolderNameException, InvalidAuthException {
        String tokenUsername = jwtTokenProvider.getUsernameFromToken(token);

        if (tokenUsername != null && tokenUsername.equals((username))) {
            User user = userService.findUserByUsername(username);

            // Kolla om mappen redan existerar för användaren
            if (folderRepository.existsByFolderNameAndUser(folderName, user)) {
                throw new InvalidFolderNameException("A folder with the name '" + folderName + "' already exists for the user '" + username + "'");
            }

            Folder newFolder = new Folder(folderName, user);
            return folderRepository.save(newFolder);
        } else {
            throw new InvalidAuthException("You are not authorized for that request");
        }
    }
}