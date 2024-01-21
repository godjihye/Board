package com.jhshin.board.controller;

import com.jhshin.board.entity.UserEntity;
import com.jhshin.board.model.PostDTO;
import com.jhshin.board.model.UserDTO;
import com.jhshin.board.service.PostService;
import com.jhshin.board.service.UserService;
import com.jhshin.board.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @GetMapping("/board/write")
    public String writeForm(){
        return "post/write";
    }

    @PostMapping("/board/write")
    @PreAuthorize("isAuthenticated()")
    public String addPost(@RequestParam("title")String title,
                          @RequestParam("content")String content,
                          Principal principal){
        if (title == null || content == null) {
            // 유효하지 않은 입력 처리
            return "redirect:/errorPage";
        }
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle(title);
        System.out.println("title = " + title + ", content = " + content + ", principal = " + principal);
        postDTO.setContent(content);
        postDTO.setCreatedAt(LocalDateTime.now());
        postDTO.setWriter(UserDTO.toUserDTO(userService.getUser(principal.getName())));

        System.out.println(postDTO.getContent());
        postService.add(postDTO);
        return "/board";
    }

    @GetMapping("/board")
    public String findAllPosts(Model model){
        List<PostDTO> postList = postService.findAll();
        model.addAttribute("posts", postList);
        return "post/list";
    }

    @GetMapping("/board/{id}")
    public String findPost(@PathVariable Long id, Model model){
        PostDTO post = postService.find(id);
        model.addAttribute("post", post);
        return "post/view";
    }
}
