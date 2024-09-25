package com.mendonca.file.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Directory {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "directoryFather", cascade = CascadeType.ALL)
    private List<Directory> subDirectories;

    @ManyToOne
    @JoinColumn(name = "directory_father_id") 
    private Directory directoryFather;

    @OneToMany(mappedBy = "directory", cascade = CascadeType.ALL)
    private List<File> files;

    public Directory(String name) {
        this.name = name;
    }
}
