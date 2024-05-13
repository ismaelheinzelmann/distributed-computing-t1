package ine5418.ismael.matheus.repository.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table
public class FileEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private String filename;
    @ManyToOne
    private UserEntity user;
    private String path;
}
