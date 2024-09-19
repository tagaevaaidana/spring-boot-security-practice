package peaksoft.springbootsecuritypractice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.springbootsecuritypractice.model.RoleEntity;
import peaksoft.springbootsecuritypractice.repository.RoleRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public List<RoleEntity> findAll(){
        return roleRepository.findAll();
    }

    public  RoleEntity findByName(String name){
        return roleRepository.findByName(name);
    }

}
