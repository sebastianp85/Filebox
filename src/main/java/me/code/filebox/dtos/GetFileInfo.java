package me.code.filebox.dtos;

import jakarta.annotation.Resource;
import lombok.Getter;
import me.code.filebox.models.FileEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Getter
public class GetFileInfo {

    private Resource resource;
    private FileEntity fileEntity;

    public GetFileInfo(Resource resource, FileEntity fileEntity) {
        this.resource = resource;
        this.fileEntity = fileEntity;
    }

    public ResponseEntity<Resource> toResponseEntity() {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + this.fileEntity.getFileName())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(this.resource);
    }
}
