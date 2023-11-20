package me.code.filebox.repositories;

import me.code.filebox.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing User entities in the database.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Checks if a user with the given username and password exists in the database.
     *
     * @param username The username to check.
     * @param password The password to check.
     * @return True if a matching user exists, false otherwise.
     */
    @Query(value = "SELECT COUNT(u) > 0 FROM Users u WHERE u.username = :username AND u.password = :password", nativeQuery = true)
    boolean isValidUser(@Param("username") String username, @Param("password") String password);

    Optional<User> findByUsername(String username);
}