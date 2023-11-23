
package me.code.filebox.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class FileEntity {

    @Id
    @Column(name = "file_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String fileName;

    @Lob
    @Column(nullable = false)
    private byte[] data;

    @Transient
    private MultipartFile file;

    @ManyToOne
    @JoinColumn(name = "folder_id", nullable = false)
    //@JsonIgnore
    private Folder folder;

    public FileEntity(MultipartFile file, Folder folder) throws IOException {
        this.fileName = file.getOriginalFilename();
        this.folder = folder;
        this.data = file.getBytes();
    }


    public Map<String, Object> toJson() {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("image_id", this.id);
        result.put("filename", this.fileName);
        return result;
    }
}


