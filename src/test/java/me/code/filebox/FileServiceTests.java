package me.code.filebox;

import me.code.filebox.exceptions.FileDoesNotExistException;
import me.code.filebox.exceptions.InvalidAuthException;
import me.code.filebox.exceptions.InvalidFolderNameException;
import me.code.filebox.models.FileEntity;
import me.code.filebox.models.Folder;
import me.code.filebox.models.User;
import me.code.filebox.repositories.FileRepository;
import me.code.filebox.repositories.FolderRepository;
import me.code.filebox.security.JwtTokenProvider;
import me.code.filebox.services.FileService;
import me.code.filebox.services.FolderService;
import me.code.filebox.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;

@SpringBootTest
public class FileServiceTests {

    @Autowired
    FileService fileService;

    @MockBean
    FolderRepository folderRepository;

    @MockBean
    UserService userService;

    @MockBean
    JwtTokenProvider jwtTokenProvider;

    @MockBean
    FileRepository fileRepository;

    @MockBean
    FolderService folderService;

    @Test
    void uploadFileSuccess() throws InvalidFolderNameException, InvalidAuthException, IOException {
        // given
        var username = "testUser";
        var folderName = "testFolder";
        var token = "testToken";
        var file = new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
        var uploadSuccessMessage = "File uploaded successfully";

        Mockito.when(userService.findUserByUsername(username)).thenReturn(new User(username, "password"));
        Mockito.when(jwtTokenProvider.getUsernameFromToken(Mockito.anyString())).thenReturn("testUser");
        Mockito.when(folderService.getFolder(Mockito.anyString(), Mockito.any(User.class)))
                .thenReturn(new Folder("testFolder", new User("testUser", "password")));
        Mockito.when(fileRepository.save(Mockito.any())).then(invocation -> {
            var newFile = (FileEntity) invocation.getArgument(0);
            newFile.setId(1); // set a sample ID for the newly saved file
            return newFile;
        });

        // when
        var result = Assertions.assertDoesNotThrow(() -> fileService.uploadFile(folderName, username, token, file));

        // then
        Assertions.assertEquals(uploadSuccessMessage, result.getMessage());
    }

    @Test
    void deleteFileSuccess() throws InvalidAuthException, FileDoesNotExistException, IOException {
        // given
        var username = "testUser";
        var token = "testToken";
        var fileId = 1; // An example file ID
        var deleteSuccessMessage = "File deleted successfully";

        Mockito.when(jwtTokenProvider.getUsernameFromToken(Mockito.anyString())).thenReturn(username);
        Mockito.when(userService.findUserByUsername(username)).thenReturn(new User(username, "password"));
        Mockito.when(fileRepository.findById(anyInt())).thenReturn(Optional.of(createTestFileEntity()));

        // when
        var result = Assertions.assertDoesNotThrow(() -> fileService.deleteFile(username, token, fileId));

        // then
        Assertions.assertEquals(deleteSuccessMessage, result.getMessage());
    }

    private FileEntity createTestFileEntity() throws IOException {
        var folder = new Folder("testFolder", new User("testUser", "password"));
        var file = new MockMultipartFile("file", "test.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!".getBytes());
        return new FileEntity(file, folder);
    }
}