package ine5418.ismael.matheus.user.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Data
@Table
public class UserEntity {
    @Id
    @GeneratedValue
    private Integer id;

    private String user;
    private String password;
}
