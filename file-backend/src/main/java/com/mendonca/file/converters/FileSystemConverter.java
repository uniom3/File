package com.mendonca.file.converters;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.mendonca.file.dto.DirectoryDto;
import com.mendonca.file.dto.FileDto;
import com.mendonca.file.entity.Directory;
import com.mendonca.file.entity.File;

@Component
public class FileSystemConverter {

	public DirectoryDto convertToDto(Directory directory) {
		DirectoryDto dto = new DirectoryDto();
		dto.setId(directory.getId());
		dto.setName(directory.getName());
		dto.setDirectoryFatherId(
				directory.getDirectoryFather() != null ? directory.getDirectoryFather().getId() : null);
		
		  if (dto.getSubDirectoryDtos() != null) {
		  dto.setSubDirectoryDtos(directory.getSubDirectories().stream().map(this::
		  convertToDto).collect(Collectors.toList())); }
		 
		
		if (dto.getFiles() != null) {
			dto.setFiles(directory.getFiles().stream().map(this::convertToDto).collect(Collectors.toList()));
		}
		return dto;
	}
	
	 public DirectoryDto convertToDto(Directory directory, Set<Long> visitedDirectories) {
	        if (visitedDirectories.contains(directory.getId())) {
	            return new DirectoryDto(); 
	        }
	        
	        visitedDirectories.add(directory.getId());

	        DirectoryDto dto = new DirectoryDto();
	        dto.setId(directory.getId());
	        dto.setName(directory.getName());
	       dto.setDirectoryFatherId(directory.getDirectoryFather() != null ? directory.getDirectoryFather().getId() : null);

	        List<DirectoryDto> subDirectoryDtos = directory.getSubDirectories().stream()
	                .map(subDir -> convertToDto(subDir, visitedDirectories))
	                .collect(Collectors.toList());
	        dto.setSubDirectoryDtos(subDirectoryDtos);

	        List<FileDto> files = directory.getFiles().stream()
	                .map(this::convertToDto) 
	                .collect(Collectors.toList());
	        dto.setFiles(files);

	        return dto;
	    }

	public Directory dtoToEntity(DirectoryDto dto) {
		Directory directory = new Directory();
		directory.setId(dto.getId());
		directory.setName(dto.getName());
		if (dto.getDirectoryFatherId() != null) {
			Directory directoryFather = new Directory();
			directoryFather.setId(dto.getDirectoryFatherId());
			directory.setDirectoryFather(directoryFather);
		}
		return directory;
	}

	public FileDto convertToDto(File file) {
		FileDto dto = new FileDto();
		dto.setId(file.getId());
		dto.setName(file.getName());
		dto.setType(file.getType());
		dto.setSize(file.getSize());
		dto.setDirectoryId(file.getDirectory() != null ? file.getDirectory().getId() : null);
		return dto;
	}

	public File dtoToEntity(FileDto dto) {
		File file = new File();
		file.setId(dto.getId());
		file.setName(dto.getName());
		file.setType(dto.getType());
		file.setSize(dto.getSize());
		if (dto.getDirectoryId() != null) {
			Directory directory = new Directory();
			directory.setId(dto.getDirectoryId());
			file.setDirectory(directory);
		}
		return file;
	}
}
