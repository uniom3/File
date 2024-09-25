package com.mendonca.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mendonca.file.entity.Directory;
import java.util.List;


public interface DirectoryRepository extends JpaRepository<Directory, Long> {
	
	List<Directory> findByDirectoryFatherId(Long id);

}
