package me.code.filebox;

import me.code.filebox.controllers.FileController;
import me.code.filebox.dtos.UploadSuccess;
import me.code.filebox.exceptions.InvalidAuthException;
import me.code.filebox.exceptions.InvalidFolderNameException;
import me.code.filebox.security.JwtTokenProvider;
import me.code.filebox.services.FileService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
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

    @Test
    public void testUploadFile() throws InvalidAuthException, InvalidFolderNameException, IOException {
        // Skapa ett MultipartFile-objekt för testet
        MultipartFile file = mock(MultipartFile.class);

        // Mocka JwtTokenProvider:s beteende
        lenient().when(jwtTokenProvider.validate(anyString())).thenReturn(true);
        lenient().when(jwtTokenProvider.getUsernameFromToken(anyString())).thenReturn("testuser");

        // Mocka FileService:s beteende
        when(fileService.uploadFile(anyString(), anyString(), anyString(), eq(file)))
                .thenReturn(new UploadSuccess("File uploaded successfully"));

        // Kör testet
        ResponseEntity<UploadSuccess> responseEntity = fileController.uploadFile("testtoken", file, "testfolder", "testuser");

        // Kontrollera att anropet var framgångsrikt och returnerade rätt meddelande
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertEquals("File uploaded successfully", responseEntity.getBody().getMessage());

        // Kontrollera att metoden i FileService har anropats med rätt parametrar
        verify(fileService, times(1)).uploadFile("testfolder", "testuser", "testtoken", file);
    }
}