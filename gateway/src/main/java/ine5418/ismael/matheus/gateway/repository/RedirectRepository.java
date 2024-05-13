package ine5418.ismael.matheus.gateway.repository;

import ine5418.ismael.matheus.gateway.entity.RedirectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RedirectRepository extends JpaRepository<RedirectEntity, Long> {
    Optional<RedirectEntity> findByPath(String path);
}
