package com.jhshin.board.config.auth;

import com.jhshin.board.entity.UserEntity;
import com.jhshin.board.model.UserDTO;
import com.jhshin.board.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.jhshin.board.model.UserRole;
@Service
public class PrincipalDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("--------------"+username);
        Optional<UserEntity> _userEntity = userRepository.findByLoginId(username);
        if(_userEntity.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }
        UserEntity userEntity = _userEntity.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin".equals(username)) {
            // username이 admin이면 관리자(ADMIN) 권한을 얻어서 내장 권한 클래스 리스트에 넣음
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
            // 아니면 일반 사용자 권한(USER)을 얻어서 내장 권한 클래스 리스트에 넣음
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }
        return new User(userEntity.getLoginId(), userEntity.getLoginPass(), authorities);
    }
}
