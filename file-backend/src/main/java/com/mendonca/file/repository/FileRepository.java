package com.mendonca.file.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mendonca.file.entity.File;

public interface FileRepository extends JpaRepository<File, Long>{

}
