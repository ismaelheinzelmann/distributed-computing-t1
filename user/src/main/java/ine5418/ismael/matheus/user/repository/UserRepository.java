package ine5418.ismael.matheus.user.repository;

import ine5418.ismael.matheus.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUser(String user);
    Optional<UserEntity> findByUserAndPassword(String user, String password);
}
