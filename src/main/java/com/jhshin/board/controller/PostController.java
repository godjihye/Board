package com.jhshin.board.controller;

import com.jhshin.board.model.PostDTO;
import com.jhshin.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/board/write")
    public String writeForm(){
        return "post/write";
    }
    @PostMapping("/board/write")
    public String addPost(PostDTO postDTO){
        postDTO.setCreatedAt(LocalDateTime.now());
        postService.add(postDTO);
        return "post/list";
    }

    @GetMapping("/board/")
    public String findAllPosts(Model model){
        List<PostDTO> postList = postService.findAll();
        model.addAttribute("posts", postList);
        return "post/list";
    }
}
