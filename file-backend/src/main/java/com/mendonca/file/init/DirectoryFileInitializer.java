package com.mendonca.file.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.mendonca.file.dto.DirectoryDto;
import com.mendonca.file.dto.FileDto;
import com.mendonca.file.service.FileSystemService;

@Component
public class DirectoryFileInitializer implements CommandLineRunner {

    private final FileSystemService fileSystemService;

    public DirectoryFileInitializer(FileSystemService fileSystemService) {
        this.fileSystemService = fileSystemService;
    }

    @Override
    public void run(String... args) throws Exception {
        createInitialDirectoriesAndFiles();
    }

    private void createInitialDirectoriesAndFiles() {
        DirectoryDto rootDirectory = new DirectoryDto();
        rootDirectory.setName("Root");
        rootDirectory.setDirectoryFatherId(null); 
        DirectoryDto rootCreated = fileSystemService.createDirectory(rootDirectory);

        DirectoryDto documentsDirectory = new DirectoryDto();
        documentsDirectory.setName("Documents");
        documentsDirectory.setDirectoryFatherId(rootCreated.getId()); 
        DirectoryDto documentsCreated = fileSystemService.createDirectory(documentsDirectory);

        DirectoryDto musicDirectory = new DirectoryDto();
        musicDirectory.setName("Music");
        musicDirectory.setDirectoryFatherId(rootCreated.getId()); 
        DirectoryDto musicCreated = fileSystemService.createDirectory(musicDirectory);
        
        DirectoryDto imageDirectory = new DirectoryDto();
        imageDirectory.setName("Image");
        imageDirectory.setDirectoryFatherId(rootCreated.getId()); 
        DirectoryDto imageCreated = fileSystemService.createDirectory(imageDirectory);
        
        DirectoryDto archiveDirectory = new DirectoryDto();
        archiveDirectory.setName("Archive");
        archiveDirectory.setDirectoryFatherId(2L); 
        fileSystemService.createDirectory(archiveDirectory);

        FileDto exampleFile = new FileDto();
        exampleFile.setName("example.txt");
        exampleFile.setSize(1234L);
        exampleFile.setType("text/plain");
        exampleFile.setDirectoryId(documentsCreated.getId()); 
        fileSystemService.createFile(exampleFile);
        
        FileDto exampleFile1 = new FileDto();
        exampleFile1.setName("minhaMusica.mp3");
        exampleFile1.setSize(12L);
        exampleFile1.setType("text/plain");
        exampleFile1.setDirectoryId(musicCreated.getId()); 
        fileSystemService.createFile(exampleFile1);
        
        FileDto exampleFile2 = new FileDto();
        exampleFile2.setName("minhaImage.jpg");
        exampleFile2.setSize(12L);
        exampleFile2.setType("text/plain");
        exampleFile2.setDirectoryId(imageCreated.getId()); 
        fileSystemService.createFile(exampleFile2);
    }
}

