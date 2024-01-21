package com.jhshin.board.service;

import com.jhshin.board.entity.UserEntity;
import com.jhshin.board.model.UserDTO;
import com.jhshin.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder getPasswordEncoder;

    public void signup(UserDTO userDTO){
        UserEntity userEntity = UserEntity.toUserEntity(userDTO);
        userRepository.save(userEntity);
    }


    public UserDTO login(UserDTO userDTO) {
        Optional<UserEntity> loginId = userRepository.findByLoginId(userDTO.getLoginId());
        if(loginId.isPresent()){
            UserEntity userEntity = loginId.get();
            if(!getPasswordEncoder.matches(userDTO.getLoginPass(), userEntity.getLoginPass())){
                UserDTO userDTO1 = userDTO.toUserDTO(userEntity);
                return userDTO1;
            } else {
                System.out.println("비밀번호가 일치하지 않음. 로그인 실패");
                return null;
            }
        } else {
            System.out.println("아이디가 존재하지 않음. 로그인 실패");
            return null;
        }
    }


    public List<UserDTO> findAll() {
        List<UserEntity> userEntityList = userRepository.findAll();
        List<UserDTO> userDTOList = new ArrayList<>();
        for (UserEntity userEntity : userEntityList) {
            userDTOList.add(UserDTO.toUserDTO(userEntity));
        }
        return userDTOList;
    }

    public UserDTO find(Long id) {
        Optional<UserEntity> myEntity = userRepository.findById(id);
        if(myEntity.isPresent()){
            UserEntity userEntity = myEntity.get();
            return UserDTO.toUserDTO(userEntity);
        } else {
            return null;
        }

    }
}
