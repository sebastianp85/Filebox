package me.code.filebox.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Entity class representing a user in the application.
 * Utilizes the Java Persistence API (JPA) annotations for mapping to a database table.
 */
@Table(name = "users")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    // The unique identifier for the user.
    private int id;

    @Column(nullable = false, unique = true)
    // The username of the user.
    private String username;

    @Column(nullable = false)
    // The password associated with the user.
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    // List of folders owned by the user.
    private List<Folder> folders;

    /**
     * Constructor for User.
     *
     * @param username The username of the user.
     * @param password The password associated with the user.
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
