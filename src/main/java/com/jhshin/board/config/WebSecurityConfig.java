package com.jhshin.board.config;

import com.jhshin.board.config.auth.PrincipalDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity  // 요청하는 모든 url이 Security를 통과시키게 만듦
@EnableMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig {
    private final AuthenticationFailureHandler customFailureHandler;
    public WebSecurityConfig(AuthenticationFailureHandler customFailureHandler, PrincipalDetailsService principalDetailsService) {
        this.customFailureHandler = customFailureHandler;
    }
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 권한 설정
                .authorizeHttpRequests((authorizeHttpRequest) -> authorizeHttpRequest
                        .requestMatchers("/", "/board/**", "/member/register").permitAll()
                        .requestMatchers("/member/list").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .csrf((csrf) -> csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/**")))
                // 로그인 설정
                .formLogin(form -> form
                        .loginPage("/member/loginPage")
                        .loginProcessingUrl("/member/login")
                        .usernameParameter("loginId")
                        .passwordParameter("loginPass")
                        .failureHandler(customFailureHandler)
                        .defaultSuccessUrl("/board", true)
                        .permitAll()
                )
                // 로그아웃 설정
                .logout((logout) -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                        .logoutSuccessUrl("/board").invalidateHttpSession(true));

        return http.build();
    }

}