package me.code.filebox;

import me.code.filebox.controllers.FileController;
import me.code.filebox.dtos.DeleteSuccess;
import me.code.filebox.dtos.UploadSuccess;
import me.code.filebox.exceptions.FileDoesNotExistException;
import me.code.filebox.exceptions.InvalidAuthException;
import me.code.filebox.exceptions.InvalidFolderNameException;
import me.code.filebox.security.JwtTokenProvider;
import me.code.filebox.services.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FileControllerTest {

    @Mock
    private FileService fileService;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private FileController fileController;

    private MultipartFile file;

    @BeforeEach
    public void setUp() {
        // Initialisera MultipartFile för varje test
        file = mock(MultipartFile.class);

        // Mocka JwtTokenProvider:s beteende
        lenient().when(jwtTokenProvider.validate(anyString())).thenReturn(true);
        lenient().when(jwtTokenProvider.getUsernameFromToken(anyString())).thenReturn("testuser");
    }

    @Test
    public void uploadFile_Success() throws InvalidAuthException, InvalidFolderNameException, IOException {
        // Mocka FileService:s beteende
        when(fileService.uploadFile(anyString(), anyString(), anyString(), eq(file)))
                .thenReturn(new UploadSuccess("File uploaded successfully"));

        // Kör testet
        ResponseEntity<UploadSuccess> responseEntity = fileController.uploadFile("testtoken", file, "testfolder", "testuser");

        // Kontrollera att anropet var framgångsrikt och returnerade rätt meddelande
        assertAll(
                () -> assertEquals(200, responseEntity.getStatusCodeValue()),
                () -> assertEquals("File uploaded successfully", responseEntity.getBody().getMessage())
        );

        // Kontrollera att metoden i FileService har anropats med rätt parametrar
        verify(fileService, times(1)).uploadFile("testfolder", "testuser", "testtoken", file);
    }

    @Test
    public void deleteFile_Success() throws InvalidAuthException, FileDoesNotExistException {
        // Mocka FileService:s beteende
        when(fileService.deleteFile(anyString(), anyString(), anyInt()))
                .thenReturn(new DeleteSuccess("File deleted successfully"));

        // Kör testet
        ResponseEntity<DeleteSuccess> responseEntity = fileController.deleteFile("testtoken", "testuser", 1);

        // Kontrollera att anropet var framgångsrikt och returnerade rätt meddelande
        assertAll(
                () -> assertEquals(200, responseEntity.getStatusCodeValue()),
                () -> assertEquals("File deleted successfully", responseEntity.getBody().getMessage())
        );

        // Kontrollera att metoden i FileService har anropats med rätt parametrar
        verify(fileService, times(1)).deleteFile("testuser", "testtoken", 1);
    }
}
