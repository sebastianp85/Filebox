package me.code.filebox.repositories;

import me.code.filebox.models.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing FileEntity entities in the database.
 * Utilizes Spring Data JPA for database interaction.
 */
@Repository
public interface FileRepository extends JpaRepository<FileEntity, Integer> {

    /**
     * Retrieves a file entity by its unique identifier.
     *
     * @param fileId The unique identifier of the file entity.
     * @return An Optional containing the file entity if found, otherwise empty.
     */
    Optional<FileEntity> findById(int fileId);
}