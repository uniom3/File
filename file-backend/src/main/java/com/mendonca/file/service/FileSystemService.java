package com.mendonca.file.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.mendonca.file.converters.FileSystemConverter;
import com.mendonca.file.dto.DirectoryDto;
import com.mendonca.file.dto.FileDto;
import com.mendonca.file.entity.Directory;
import com.mendonca.file.entity.File;
import com.mendonca.file.exception.DirectoryNotFoundException;
import com.mendonca.file.repository.DirectoryRepository;
import com.mendonca.file.repository.FileRepository;

import jakarta.transaction.Transactional;

@Service
public class FileSystemService {

	@Autowired
	private DirectoryRepository directoryRepository;

	@Autowired
	private FileRepository fileRepository;

	@Autowired
	private FileSystemConverter converter;
	
	
	public FileSystemService(DirectoryRepository directoryRepository, FileRepository fileRepository, FileSystemConverter converter) {
        this.directoryRepository = directoryRepository;
        this.fileRepository = fileRepository;
        this.converter = converter;
    }

	@Transactional
	public DirectoryDto createDirectory(DirectoryDto dto) {

		if (dto.getDirectoryFatherId() != null) {
			Directory parentDirectory = directoryRepository.findById(dto.getDirectoryFatherId()).orElse(null);
			if (parentDirectory == null) {
				Directory newDirectory = new Directory();
				newDirectory.setId(1L);

				if (newDirectory.getId() != dto.getDirectoryFatherId()) {
					throw new DirectoryNotFoundException("Diretório pai não existe");
				}

			}
		}
		try {
			Directory newDirectory = converter.dtoToEntity(dto);
			return converter.convertToDto(directoryRepository.save(newDirectory));
		} catch (DataIntegrityViolationException e) {
			throw new RuntimeException(new DirectoryNotFoundException("Diretório pai não existe"));
		}

	}

	public DirectoryDto getDirectoryById(Long id) {
		return converter.convertToDto(directoryRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Directory not found")));
	}


	public List<DirectoryDto> getAllSubDirectoriesAndFiles() {
	    List<Directory> allDirectories = directoryRepository.findAll();
	    Set<Long> visitedDirectories = new HashSet<>();

	    return allDirectories.stream()
	            .map(directory -> converter.convertToDto(directory, visitedDirectories))
	            .filter(directoryDto -> isValidDirectoryDto(directoryDto)) 
	            .collect(Collectors.toList());
	}
	
	private boolean isValidDirectoryDto(DirectoryDto directoryDto) {
	    boolean hasIdAndName = directoryDto.getId() != null && directoryDto.getName() != null;

	    if (directoryDto.getSubDirectoryDtos() != null) {
	        List<DirectoryDto> validSubDirectories = directoryDto.getSubDirectoryDtos().stream()
	                .filter(this::isValidDirectoryDto) 
	                .collect(Collectors.toList());

	        directoryDto.setSubDirectoryDtos(validSubDirectories);
	    }

	    boolean hasFiles = directoryDto.getFiles() != null && !directoryDto.getFiles().isEmpty();

	    return hasIdAndName || (directoryDto.getSubDirectoryDtos() != null && !directoryDto.getSubDirectoryDtos().isEmpty()) || hasFiles;
	}


	@Transactional
	public DirectoryDto updateDirectory(Long id, String newName) {
		Directory directory = directoryRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Directory not found"));
		directory.setName(newName);
		return converter.convertToDto(directoryRepository.save(directory));
	}

	@Transactional
	public void deleteDirectory(Long id) {
		directoryRepository.deleteById(id);
	}

	@Transactional
	public FileDto createFile(FileDto dto) {
		Directory parentDirectory = directoryRepository.findById(dto.getDirectoryId())
				.orElseThrow(() ->  new DirectoryNotFoundException("Diretório pai não existe"));
		File file = converter.dtoToEntity(dto);
		file.setDirectory(parentDirectory);
		return converter.convertToDto(fileRepository.save(file));
	}

	public FileDto getFileById(Long id) {
		return converter.convertToDto(
				fileRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("File not found")));
	}

	public List<FileDto> listAllFiles() {
		return fileRepository.findAll().stream().map(converter::convertToDto).collect(Collectors.toList());
	}

	@Transactional
	public FileDto updateFile(Long id, String newName, String newType, long newSize) {
		File file = fileRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("File not found"));
		file.setName(newName);
		file.setType(newType);
		file.setSize(newSize);
		return converter.convertToDto(fileRepository.save(file));
	}

	@Transactional
	public void deleteFile(Long id) {
		fileRepository.deleteById(id);
	}

}
