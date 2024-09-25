package com.mendonca.file.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class FileDto {

    private Long id;
    private String name;
    private Long size;
    private String type;
    private Long directoryId;

}

