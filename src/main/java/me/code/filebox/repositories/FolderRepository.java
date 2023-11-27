package me.code.filebox.repositories;

import me.code.filebox.models.Folder;
import me.code.filebox.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing Folder entities in the database.
 * Utilizes Spring Data JPA for database interaction.
 */
public interface FolderRepository extends JpaRepository<Folder, Integer> {

    /**
     * Checks if a folder with the given name exists for a specific user.
     *
     * @param folderName The name of the folder to check.
     * @param user       The user to whom the folder belongs.
     * @return True if the folder exists for the user, false otherwise.
     */
    boolean existsByFolderNameAndUser(String folderName, User user);

    /**
     * Retrieves a folder by its name and associated user.
     *
     * @param folderName The name of the folder to retrieve.
     * @param user       The user to whom the folder belongs.
     * @return An Optional containing the folder if found, otherwise empty.
     */
    Optional<Folder> findByFolderNameAndUser(String folderName, User user);
}
