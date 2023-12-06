package me.code.filebox.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Entity class representing a file in the application.
 * Utilizes the Java Persistence API (JPA) annotations for mapping to a database table.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class FileEntity {

    @Id
    @Column(name = "file_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    // The unique identifier for the file.
    private int id;

    @Column(nullable = false)
    // The name of the file.
    private String fileName;

    @Lob
    @Column(nullable = false)
    // The byte data of the file.
    private byte[] data;

    @Column(name = "size")
    // The size of the file in bytes
    private long size;

    @Transient
    // Transient field for storing the file during file upload.
    private MultipartFile file;

    @ManyToOne
    @JoinColumn(name = "folder_id", nullable = false)
    // The folder to which the file belongs.
    private Folder folder;

    /**
     * Constructor for FileEntity.
     *
     * @param file   The multipart file containing the file data.
     * @param folder The folder to which the file belongs.
     * @throws IOException If an I/O error occurs during file processing.
     */
    public FileEntity(MultipartFile file, Folder folder) throws IOException {
        this.fileName = file.getOriginalFilename();
        this.folder = folder;
        this.data = file.getBytes();
        this.size = file.getSize();
    }
}
