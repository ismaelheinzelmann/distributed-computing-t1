package ine5418.ismael.matheus.loadbalancer.repository;

import ine5418.ismael.matheus.loadbalancer.entity.InstanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InstanceRepository extends JpaRepository<InstanceEntity, Long> {
    Optional<InstanceEntity> findByInstancePath(String instancePath);
    Optional<InstanceEntity> findById(Integer instanceId);
}
