package com.jhshin.board.config.auth;

import com.jhshin.board.entity.UserEntity;
import com.jhshin.board.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;

public class PrincipalDetails implements UserDetails {
    private PasswordEncoder passwordEncoder;
    private UserDTO userDTO;
    public PrincipalDetails(UserDTO userDTO){
        this.userDTO = userDTO;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        Collection<GrantedAuthority> collect = new ArrayList<>();
        collect.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return userDTO.getRole();
            }
        });

        return collect;
    }
    @Override
    public String getPassword() {
        return userDTO.getLoginPass();
    }

    @Override
    public String getUsername() {
        return userDTO.getLoginId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
