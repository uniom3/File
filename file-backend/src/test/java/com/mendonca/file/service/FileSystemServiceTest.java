package com.mendonca.file.service;

import com.mendonca.file.converters.FileSystemConverter;
import com.mendonca.file.dto.DirectoryDto;
import com.mendonca.file.dto.FileDto;
import com.mendonca.file.entity.Directory;
import com.mendonca.file.entity.File;
import com.mendonca.file.exception.DirectoryNotFoundException;
import com.mendonca.file.repository.DirectoryRepository;
import com.mendonca.file.repository.FileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FileSystemServiceTest {

    @Mock
    private DirectoryRepository directoryRepository;

    @Mock
    private FileRepository fileRepository;

    @Mock
    private FileSystemConverter converter;

    @InjectMocks
    private FileSystemService fileSystemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateDirectory_withDirectoryFather() {
        DirectoryDto dto = new DirectoryDto();
        dto.setDirectoryFatherId(1L);
        dto.setName("New Directory");

        Directory parentDirectory = new Directory();
        parentDirectory.setId(1L);

        Directory newDirectory = new Directory();
        newDirectory.setName("New Directory");

        when(directoryRepository.findById(1L)).thenReturn(Optional.of(parentDirectory));
        when(converter.dtoToEntity(dto)).thenReturn(newDirectory);
        when(directoryRepository.save(any(Directory.class))).thenReturn(newDirectory);
        when(converter.convertToDto(newDirectory)).thenReturn(dto);

        DirectoryDto result = fileSystemService.createDirectory(dto);

        assertNotNull(result);
        assertEquals("New Directory", result.getName());
        verify(directoryRepository, times(1)).save(any(Directory.class));
    }

    @Test
    void testCreateDirectory_withNonExistentFather() {
        DirectoryDto dto = new DirectoryDto();
        dto.setDirectoryFatherId(99L);
        dto.setName("New Directory");

        when(directoryRepository.findById(99L)).thenReturn(Optional.empty());

        DirectoryNotFoundException exception = assertThrows(DirectoryNotFoundException.class, () -> {
            fileSystemService.createDirectory(dto);
        });

        assertEquals("Diretório pai não existe", exception.getMessage());
        verify(directoryRepository, never()).save(any(Directory.class));
    }

    @Test
    void testCreateDirectory_DataIntegrityViolationException() {
        DirectoryDto dto = new DirectoryDto();
        dto.setName("New Directory");

        when(converter.dtoToEntity(dto)).thenThrow(DataIntegrityViolationException.class);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            fileSystemService.createDirectory(dto);
        });

        assertTrue(exception.getMessage().contains("Diretório pai não existe"));
    }

    @Test
    void testGetDirectoryById() {
        Directory directory = new Directory();
        directory.setId(1L);
        directory.setName("Test Directory");

        DirectoryDto dto = new DirectoryDto();
        dto.setId(1L);
        dto.setName("Test Directory");

        when(directoryRepository.findById(1L)).thenReturn(Optional.of(directory));
        when(converter.convertToDto(directory)).thenReturn(dto);

        DirectoryDto result = fileSystemService.getDirectoryById(1L);

        assertNotNull(result);
        assertEquals("Test Directory", result.getName());
        verify(directoryRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateFile() {
        FileDto fileDto = new FileDto();
        fileDto.setName("Test File");
        fileDto.setDirectoryId(1L);

        Directory parentDirectory = new Directory();
        parentDirectory.setId(1L);

        File file = new File();
        file.setName("Test File");
        file.setDirectory(parentDirectory);

        when(directoryRepository.findById(1L)).thenReturn(Optional.of(parentDirectory));
        when(converter.dtoToEntity(fileDto)).thenReturn(file);
        when(fileRepository.save(any(File.class))).thenReturn(file);
        when(converter.convertToDto(file)).thenReturn(fileDto);

        FileDto result = fileSystemService.createFile(fileDto);

        assertNotNull(result);
        assertEquals("Test File", result.getName());
        verify(fileRepository, times(1)).save(any(File.class));
    }

    @Test
    void testGetFileById() {
        File file = new File();
        file.setId(1L);
        file.setName("Test File");

        FileDto dto = new FileDto();
        dto.setId(1L);
        dto.setName("Test File");

        when(fileRepository.findById(1L)).thenReturn(Optional.of(file));
        when(converter.convertToDto(file)).thenReturn(dto);

        FileDto result = fileSystemService.getFileById(1L);

        assertNotNull(result);
        assertEquals("Test File", result.getName());
        verify(fileRepository, times(1)).findById(1L);
    }
}
