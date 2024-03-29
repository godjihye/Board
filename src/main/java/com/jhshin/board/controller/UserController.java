package com.jhshin.board.controller;

import com.jhshin.board.model.UserDTO;
import com.jhshin.board.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.time.LocalDate.now;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Autowired
    private PasswordEncoder getPasswordEncoder;

    @GetMapping("/welcomeback")
    public String welcome() {
        return "user/welcome";
    }

    @GetMapping("/member/loginPage")
    public String loginForm(@RequestParam(value="error", required = false) String error,
                            @RequestParam(value="exception", required = false) String exception,
                            Model model) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        System.out.println("error = " + error + ", exception = " + exception + ", model = " + model);
        return "user/login";
    }

    // 로그인 -> Spring Security에 맡김.
    /*
    @PostMapping("/member/login")
    public String login(@ModelAttribute UserDTO userDTO, HttpSession session) {
        UserDTO loginResult = userService.login(userDTO);
        if(loginResult!=null) {
            session.setAttribute("user", loginResult);
            return "user/main";
        } else {
            return "user/signup";
        }
    }
    */

    @GetMapping("/member/register")
    public String signupForm() {
        return "user/signup";
    }

    @PostMapping("/member/register")
    public String signup(@ModelAttribute @Valid UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            return "member/register";
        }
        userDTO.setCreatedAt(now());
        String encPass = getPasswordEncoder.encode(userDTO.getLoginPass());
        userDTO.setLoginPass(encPass);
        userDTO.setRole("ROLE_USER");
        userService.signup(userDTO);
        return "redirect:/user/login";
    }

    @GetMapping("/member/list")
    public String findAll(Model model){
        List<UserDTO> userDTOList = userService.findAll();
        model.addAttribute("userDTOList",userDTOList);
        return "user/list";
    }

    @PostMapping("/member/mypage")
    public String findUser(@RequestParam("userId") Long id, Model model){
        UserDTO userDTO = userService.find(id);
        model.addAttribute("user", userDTO);
        return "user/mypage";
    }

    @GetMapping("/member/logout")
    public String logout(){
        return "user/logout";
    }
}
