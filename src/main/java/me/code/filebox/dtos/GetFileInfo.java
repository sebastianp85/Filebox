package me.code.filebox.dtos;

import jakarta.annotation.Resource;
import lombok.Getter;
import me.code.filebox.models.FileEntity;

@Getter
public class GetFileInfo {

    private Resource resource;
    private FileEntity fileEntity;

    public GetFileInfo(Resource resource, FileEntity fileEntity) {
        this.resource = resource;
        this.fileEntity = fileEntity;
    }
}


