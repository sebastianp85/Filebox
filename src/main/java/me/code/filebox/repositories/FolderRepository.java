package me.code.filebox.repositories;

import me.code.filebox.models.Folder;
import me.code.filebox.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FolderRepository extends JpaRepository<Folder, Integer> {

    boolean existsByFolderNameAndUser(String folderName, User user);

    Optional<Folder> findByFolderNameAndUser(String folderName, User user);
}
