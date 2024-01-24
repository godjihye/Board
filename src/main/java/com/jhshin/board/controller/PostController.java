package com.jhshin.board.controller;

import com.jhshin.board.model.CommentDTO;
import com.jhshin.board.model.PostDTO;
import com.jhshin.board.model.UserDTO;
import com.jhshin.board.service.CommentService;
import com.jhshin.board.service.PostService;
import com.jhshin.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final UserService userService;
    private final CommentService commentService;

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
            return "redirect:";
        }
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle(title);
        postDTO.setContent(content);
        postDTO.setCreatedAt(LocalDateTime.now());
        postDTO.setWriter(UserDTO.toUserDTO(userService.getUser(principal.getName())));
        postService.addPost(postDTO);
        return "redirect:/board";
    }

    @GetMapping("/board")
    public String findAllPosts(Model model){
        List<PostDTO> postList = postService.findAllPosts();
        model.addAttribute("posts", postList);
        return "post/list";
    }

    @GetMapping("/board/{id}")
    public String findPost(@PathVariable Long id, Model model){
        PostDTO post = postService.findPost(id);
        List<CommentDTO> comments = commentService.getCommentsByPost(id); // 댓글 목록 조회

        model.addAttribute("post", post);
        model.addAttribute("comments", comments);
        return "post/view";
    }

    @PostMapping("/board/delete")
    @PreAuthorize("isAuthenticated()")
    public String removePost(@RequestParam(value = "id") String postId) {
        postService.removePost(Long.parseLong(postId));
        return "redirect:/board";
    }

    @GetMapping("/board/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public String editPostForm(@PathVariable Long id, Model model) {
        PostDTO post = postService.findPost(id);
        model.addAttribute("post", post);
        return "post/editForm";
    }

    @PostMapping("/board/edit")
    public String editPost(@RequestParam(value = "id")String postId,
                           @RequestParam(value = "title")String title,
                           @RequestParam(value = "content") String content) {
        PostDTO postDTO = new PostDTO();
        postDTO.setId(Long.parseLong(postId));
        postDTO.setTitle(title);
        postDTO.setContent(content);
        postDTO.setUpdatedAt(LocalDateTime.now());
        postService.editPost(postDTO);
        return "redirect:/board/" + postId;
    }
}
