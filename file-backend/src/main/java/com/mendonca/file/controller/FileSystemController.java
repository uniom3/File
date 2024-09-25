package com.mendonca.file.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mendonca.file.dto.DirectoryDto;
import com.mendonca.file.dto.FileDto;
import com.mendonca.file.service.FileSystemService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/filesystem")
@Tag(name = "FileSystem", description = "API for managing directories and files")
public class FileSystemController {

    @Autowired
    private FileSystemService fileSystemService;

    @Operation(summary = "Create a new directory", description = "Creates a new directory based on the provided data.")
    @PostMapping("/directories")
    public DirectoryDto createDirectory(@RequestBody DirectoryDto directoryDto) {
        return fileSystemService.createDirectory(directoryDto);
    }

    @Operation(summary = "Get directory by ID", description = "Returns the data of a directory based on the provided ID.")
    @GetMapping("/directories/{id}")
    public DirectoryDto getDirectory(@Parameter(description = "ID of the directory to be retrieved") @PathVariable Long id) {
        return fileSystemService.getDirectoryById(id);
    }

    @Operation(summary = "List all subdirectories and files", description = "Returns a list of all subdirectories and files present in the system.")
    @GetMapping("/subdirectories-files")
    public ResponseEntity<List<DirectoryDto>> getAllSubDirectoriesAndFiles() {
        List<DirectoryDto> directories = fileSystemService.getAllSubDirectoriesAndFiles();
        return ResponseEntity.ok(directories);
    }

    @Operation(summary = "Update directory", description = "Updates the name of an existing directory based on the provided ID.")
    @PutMapping("/directories/{id}")
    public DirectoryDto updateDirectory(
            @Parameter(description = "ID of the directory to be updated") @PathVariable Long id,
            @RequestBody DirectoryDto directoryDto) {
        return fileSystemService.updateDirectory(id, directoryDto.getName());
    }

    @Operation(summary = "Delete directory", description = "Deletes a directory based on the provided ID.")
    @DeleteMapping("/directories/{id}")
    public void deleteDirectory(@Parameter(description = "ID of the directory to be deleted") @PathVariable Long id) {
        fileSystemService.deleteDirectory(id);
    }

    @Operation(summary = "Create a new file", description = "Creates a new file based on the provided data.")
    @PostMapping("/files")
    public FileDto createFile(@RequestBody FileDto fileDto) {
        return fileSystemService.createFile(fileDto);
    }

    @Operation(summary = "Get file by ID", description = "Returns the data of a file based on the provided ID.")
    @GetMapping("/files/{id}")
    public FileDto getFile(@Parameter(description = "ID of the file to be retrieved") @PathVariable Long id) {
        return fileSystemService.getFileById(id);
    }

    @Operation(summary = "Update file", description = "Updates the details of an existing file based on the provided ID.")
    @PutMapping("/files/{id}")
    public FileDto updateFile(
            @Parameter(description = "ID of the file to be updated") @PathVariable Long id,
            @RequestParam String newName,
            @RequestParam String newType,
            @RequestParam long newSize) {
        return fileSystemService.updateFile(id, newName, newType, newSize);
    }

    @Operation(summary = "Delete file", description = "Deletes a file based on the provided ID.")
    @DeleteMapping("/files/{id}")
    public void deleteFile(@Parameter(description = "ID of the file to be deleted") @PathVariable Long id) {
        fileSystemService.deleteFile(id);
    }

    @Operation(summary = "List all files", description = "Returns a list of all files present in the system.")
    @GetMapping("/files")
    public List<FileDto> listAllFiles() {
        return fileSystemService.listAllFiles();
    }
}
