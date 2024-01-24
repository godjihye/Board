package com.jhshin.board.entity;

import com.jhshin.board.model.UserDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.time.LocalDate;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "user_tbl")
@EntityScan(basePackages = "com.jhshin.board.entity")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String loginPass;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate createdAt;

    @Column(unique = true, nullable = false)
    private String nickname;

    public static UserEntity toUserEntity(UserDTO userDTO){
        UserEntity userEntity = new UserEntity();
        userEntity.setLoginId(userDTO.getLoginId());
        userEntity.setLoginPass(userDTO.getLoginPass());
        userEntity.setName(userDTO.getName());
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setNickname(userDTO.getNickname());
        userEntity.setCreatedAt(userDTO.getCreatedAt());
        return userEntity;
    }
}
