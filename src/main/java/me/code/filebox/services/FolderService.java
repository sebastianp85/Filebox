package me.code.filebox.services;

import me.code.filebox.models.Folder;
import me.code.filebox.models.User;
import me.code.filebox.repositories.FolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FolderService {
    private final FolderRepository folderRepository;
    private final UserService userService;

    @Autowired
    public FolderService(FolderRepository folderRepository, UserService userService) {
        this.folderRepository = folderRepository;
        this.userService = userService;
    }

    public Folder createFolder(String folderName, String username) {
        User user = userService.findUserByUsername(username);
        Folder newFolder = new Folder(folderName, user);
        return folderRepository.save(newFolder);
    }

}
