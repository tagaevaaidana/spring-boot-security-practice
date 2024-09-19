package peaksoft.springbootsecuritypractice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peaksoft.springbootsecuritypractice.model.RoleEntity;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

     List<RoleEntity> findAll();
     RoleEntity findByName(String name);
}
