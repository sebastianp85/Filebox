
package me.code.filebox.repositories;

import me.code.filebox.models.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Integer> {


    Optional<FileEntity> findById(int fileId);

    // Hämta alla filer för en specifik mapp
    List<FileEntity> findByFolderId(int folderId);

    // Hitta en fil med ett specifikt namn i en mapp
    Optional<FileEntity> findByFileNameAndFolderId(String fileName, int folderId);

}

