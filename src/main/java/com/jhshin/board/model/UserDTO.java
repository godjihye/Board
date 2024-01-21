package com.jhshin.board.model;

import com.jhshin.board.entity.UserEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String loginId;
    private String loginPass;
    private String email;
    private String name;
    private LocalDate createdAt;
    private String nickname;
    private String role;

    public static UserDTO toUserDTO(UserEntity userEntity) {
        UserDTO dto = new UserDTO();
        dto.setId(userEntity.getId());
        dto.setName(userEntity.getName());
        dto.setEmail(userEntity.getEmail());
        dto.setNickname(userEntity.getNickname());
        dto.setLoginId(userEntity.getLoginId());
        dto.setLoginPass(userEntity.getLoginPass());
        dto.setCreatedAt(userEntity.getCreatedAt());
        dto.setRole(userEntity.getRole());
        return dto;
    }
}
