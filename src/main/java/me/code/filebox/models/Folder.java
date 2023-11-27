package me.code.filebox.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Entity class representing a folder in the application.
 * Utilizes the Java Persistence API (JPA) annotations for mapping to a database table.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    // The unique identifier for the folder.
    private int id;

    @Column(nullable = false)
    // The name of the folder.
    private String folderName;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    // The user to whom the folder belongs.
    private User user;

    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true)
    // List of files contained in the folder.
    private List<FileEntity> files;

    /**
     * Constructor for Folder.
     *
     * @param name The name of the folder.
     * @param user The user to whom the folder belongs.
     */
    public Folder(String name, User user) {
        this.folderName = name;
        this.user = user;
    }
}