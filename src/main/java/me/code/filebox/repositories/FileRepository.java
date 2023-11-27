package me.code.filebox.repositories;

import me.code.filebox.models.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Integer> {

    Optional<FileEntity> findById(int fileId);
}
