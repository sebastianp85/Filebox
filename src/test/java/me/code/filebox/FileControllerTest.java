package me.code.filebox;

import me.code.filebox.controllers.FileController;
import me.code.filebox.dtos.DeleteSuccess;
import me.code.filebox.dtos.UploadSuccess;
import me.code.filebox.exceptions.InvalidAuthException;
import me.code.filebox.exceptions.InvalidFolderNameException;
import me.code.filebox.exceptions.FileDoesNotExistException;
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

/**
 * Test for the FileController class.
 */
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
        // Initialize MultipartFile for each test
        file = mock(MultipartFile.class);

        // Mock JwtTokenProvider behavior
        lenient().when(jwtTokenProvider.validate(anyString())).thenReturn(true);
        lenient().when(jwtTokenProvider.getUsernameFromToken(anyString())).thenReturn("testuser");
    }

    /**
     * Test for the successful file upload functionality.
     *
     * @throws InvalidAuthException        If authentication is invalid.
     * @throws InvalidFolderNameException  If the folder name is invalid.
     * @throws IOException                If an I/O exception occurs.
     */
    @Test
    public void uploadFile_Success() throws InvalidAuthException, InvalidFolderNameException, IOException {
        // Mock FileService behavior
        when(fileService.uploadFile(anyString(), anyString(), anyString(), eq(file)))
                .thenReturn(new UploadSuccess("File uploaded successfully"));

        // Run the test
        ResponseEntity<UploadSuccess> responseEntity = fileController.uploadFile("testtoken", file, "testfolder", "testuser");

        // Check that the call was successful and returned the correct message
        assertAll(
                () -> assertEquals(200, responseEntity.getStatusCodeValue()),
                () -> assertEquals("File uploaded successfully", responseEntity.getBody().getMessage())
        );

        // Verify that the method in FileService was called with the correct parameters
        verify(fileService, times(1)).uploadFile("testfolder", "testuser", "testtoken", file);
    }

    /**
     * Test for the successful file deletion functionality.
     *
     * @throws InvalidAuthException       If authentication is invalid.
     * @throws FileDoesNotExistException If the file does not exist.
     */
    @Test
    public void deleteFile_Success() throws InvalidAuthException, FileDoesNotExistException {
        // Mock FileService behavior
        when(fileService.deleteFile(anyString(), anyString(), anyInt()))
                .thenReturn(new DeleteSuccess("File deleted successfully"));

        // Run the test
        ResponseEntity<DeleteSuccess> responseEntity = fileController.deleteFile("testtoken", "testuser", 1);

        // Check that the call was successful and returned the correct message
        assertAll(
                () -> assertEquals(200, responseEntity.getStatusCodeValue()),
                () -> assertEquals("File deleted successfully", responseEntity.getBody().getMessage())
        );

        // Verify that the method in FileService was called with the correct parameters
        verify(fileService, times(1)).deleteFile("testuser", "testtoken", 1);
    }
}
