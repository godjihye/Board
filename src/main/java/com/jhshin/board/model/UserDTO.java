package com.jhshin.board.model;

import com.jhshin.board.entity.UserEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor

public class UserDTO {

    private Long id;
    @NotEmpty(message = "아이디를 입력하세요.")
    @Size(min = 4, max = 10)
    private String loginId;

    @NotEmpty(message = "비밀번호를 입력하세요.")
    private String loginPass;

    @NotEmpty(message = "이메일을 입력하세요.")
    private String email;

    @NotEmpty(message = "이름을 입력하세요.")
    private String name;

    private LocalDate createdAt;

    @NotEmpty(message = "닉네임을 입력하세요.")
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
        return dto;
    }
}
