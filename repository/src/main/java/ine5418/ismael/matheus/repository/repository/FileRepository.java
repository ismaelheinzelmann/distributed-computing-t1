package ine5418.ismael.matheus.repository.repository;

import ine5418.ismael.matheus.repository.entity.FileEntity;
import ine5418.ismael.matheus.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findByUser(UserEntity user);
    Optional<FileEntity> findByUserAndFilename(UserEntity user, String filename);
}
