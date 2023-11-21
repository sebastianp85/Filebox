package me.code.filebox.repositories;

import me.code.filebox.models.Folder;
import me.code.filebox.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FolderRepository extends JpaRepository<Folder, Integer> {

    @Query(value = "SELECT u FROM User u JOIN FETCH u.folders WHERE u.username = :username", nativeQuery = true)
    Optional<User> findUserWithFoldersByUsername(@Param("username") String username);
/*
    @Query(value = "SELECT COUNT(f) > 0 FROM Folder f WHERE f.name = :name", nativeQuery = true)
    boolean isValidFolder(@Param("name") String name);

    Optional<Folder> findByFolderName(String name);
     */

}


