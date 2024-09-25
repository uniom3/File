package com.mendonca.file.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DirectoryDto {

    private Long id;
    private String name;
    private Long directoryFatherId;
    private List<DirectoryDto> subDirectoryDtos;
    private List<FileDto> files;

   
}

