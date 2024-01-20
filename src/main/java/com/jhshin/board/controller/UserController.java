package com.jhshin.board.controller;

import com.jhshin.board.model.UserDTO;
import com.jhshin.board.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDate.now;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/member/login")
    public String loginForm() {
        return "login";
    }
    @PostMapping("/member/login")
    public String login(@ModelAttribute UserDTO userDTO, HttpSession session) {
        UserDTO loginResult = userService.login(userDTO);
        if(loginResult!=null) {
            session.setAttribute("user", loginResult);
            return "main";
        } else {
            return "signup";
        }
    }

    @GetMapping("/member/register")
    public String signupForm() {
        return "signup";
    }

    @PostMapping("/member/register")
    public String signup(@ModelAttribute UserDTO userDTO) {
        userDTO.setCreatedAt(now());
        userService.signup(userDTO);
        return "login";
    }

    @GetMapping("/member/list")
    public String findAll(Model model){
        List<UserDTO> userDTOList = userService.findAll();
        model.addAttribute("userDTOList",userDTOList);
        return "list";
    }

    @PostMapping("/member/mypage")
    public String findUser(@RequestParam("userId") Long id, Model model){
        System.out.println("id = " + id + ", model = " + model);
        UserDTO userDTO = userService.find(id);
        model.addAttribute("user", userDTO);
        return "mypage";
    }
}
