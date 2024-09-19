package peaksoft.springbootsecuritypractice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import peaksoft.springbootsecuritypractice.dto.UserRequest;
import peaksoft.springbootsecuritypractice.dto.UserResponse;
import peaksoft.springbootsecuritypractice.mapper.UserMapper;
import peaksoft.springbootsecuritypractice.model.RoleEntity;
import peaksoft.springbootsecuritypractice.model.UserEntity;
import peaksoft.springbootsecuritypractice.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final RoleService roleService;

//    @Transactional
//    public UserResponse save(UserRequest userRequest) {
//        UserEntity userEntity = userMapper.mapToEntity(userRequest);
//        List<RoleEntity> roleEntityList = roleService.findAll();
//        RoleEntity roleEntity;
//        if (roleEntityList.isEmpty()) {
//            roleEntity = new RoleEntity();
//            if (userRequest.getName().equalsIgnoreCase("ADMIN")) {
//                roleEntity.setName("ADMIN");
//            } else {
//                roleEntity.setName("USER");
//            }
//        } else {
//            if (userRequest.getName().equalsIgnoreCase("ADMIN")) {
//                roleEntity = roleService.findByName("ADMIN");
//                if (roleEntity == null){
//                    roleEntity = new RoleEntity();
//                    roleEntity.setName("ADMIN");
//                }
//            } else {
//                roleEntity = roleService.findByName("USER");
//                if (roleEntity == null){
//                    roleEntity = new RoleEntity();
//                    roleEntity.setName("USER");
//                }
//            }
//        }
//        List<UserEntity> userEntityList = roleEntity.getUserEntityList();
//        userEntityList.add(userEntity);
//        roleEntity.setUserEntityList(userEntityList);
//        userEntity.setRoles(Collections.singletonList(roleEntity));
//        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
//        userRepository.save(userEntity);
//        return userMapper.mapToResponse(userEntity);


//    @Transactional
//    public UserResponse save(UserRequest userRequest) {
//        // Маппим запрос на сущность пользователя
//        UserEntity userEntity = userMapper.mapToEntity(userRequest);
//
//        // Определяем имя роли (ADMIN или USER)
//        String roleName = userRequest.getName().equalsIgnoreCase("ADMIN") ? "ADMIN" : "USER";
//
//        // Пытаемся найти роль по имени
//        RoleEntity roleEntity = roleService.findByName(roleName);
//
//        // Если роль не найдена, создаем новую
//        if (roleEntity == null) {
//            roleEntity = new RoleEntity();
//            roleEntity.setName(roleName);
//        }
//
//        // Добавляем пользователя к списку пользователей роли
//        roleEntity.getUserEntityList().add(userEntity);
//
//        // Присваиваем роль пользователю
//        userEntity.setRoles(Collections.singletonList(roleEntity));
//      List<RoleEntity> roleEntityList = userEntity.getRoles();
//        roleEntityList.add(roleEntity);
//
//        // Шифруем пароль
//        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
//
//        // Сохраняем пользователя в базе данных
//        userRepository.save(userEntity);
//
//        // Возвращаем ответ
//        return userMapper.mapToResponse(userEntity);
//    }

    @Transactional
    public UserResponse save(UserRequest userRequest) {
        UserEntity userEntity = userMapper.mapToEntity(userRequest);
        String roleName = userRequest.getName().equalsIgnoreCase("ADMIN") ? "ADMIN" : "USER";
        RoleEntity roleEntity = roleService.findByName(roleName);
        if (roleEntity == null) {
            roleEntity = new RoleEntity()
                    .setName(roleName);
        }
//        roleEntity.getUserEntityList().add(userEntity);
        List<RoleEntity> roleEntityList = userEntity.getRoles();
        List<UserEntity> userEntityList = new ArrayList<>();
        roleEntity.setUserEntityList(userEntityList);
        userEntity.setRoles(roleEntityList);
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userEntityList.add(userEntity);
        roleEntityList.add(roleEntity);
        return userMapper.mapToResponse(userRepository.save(userEntity));
    }
    //todo: Метод возращает userEntity , a должен возвращать userResponse
//    public UserEntity findById(Long id){
//        return  userRepository.findById(id).get();
//    }

    public UserResponse findById(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        if (userEntity == null) {
            return null;
        }
        UserResponse userResponse = userMapper.mapToResponse(userEntity);
        return userResponse;
    }


//   //todo: Метод возращает userEntity , a должен возвращать userResponse
    public List<UserResponse> findAll(){
        List<UserEntity> userEntityList = userRepository.findAll();
        List<UserResponse> userResponseList = new ArrayList<>();
        //todo альтернативо mapping list
//        for (UserEntity userEntity : userEntityList) {
//            UserResponse userResponse = userMapper.mapToResponse(userEntity);
//            userResponseList.add(userResponse);
//        }
        userResponseList = userEntityList
                .stream()
                .map(userMapper::mapToResponse)
                .toList();
        return userResponseList;
    }

    //todo: Метод возращает userEntity , a должен возвращать userResponse
    //todo: Метод в качестве параметра дожен брать UserRequest , вместо UserEntity
    public UserResponse updateById(Long id, UserRequest userRequest){
       UserEntity userEntity = userRepository.findById(id).orElse(null);
       userEntity.setName(userRequest.getName());
       userEntity.setPassword(userRequest.getPassword());
       userRepository.save(userEntity);
       return userMapper.mapToResponse(userEntity);
    }

//    //todo: Метод возращает userEntity , a должен возвращать userResponse
    public UserResponse deleteById(Long id){
        UserEntity userEntity = userRepository.findById(id).orElse(null);
        UserResponse userResponse = userMapper.mapToResponse(userEntity);
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return userResponse;
        } else {
            return null;
        }

    }


}
