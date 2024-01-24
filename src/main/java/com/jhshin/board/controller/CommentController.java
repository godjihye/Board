package com.jhshin.board.controller;

import com.jhshin.board.model.PostDTO;
import com.jhshin.board.model.UserDTO;
import com.jhshin.board.service.CommentService;
import com.jhshin.board.service.PostService;
import com.jhshin.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.jhshin.board.model.CommentDTO;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final PostService postService;
    private final UserService userService;
    // 댓글 조회
    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDTO>> getCommentsByPost(@PathVariable Long postId) {
        List<CommentDTO> comments = commentService.getCommentsByPost(postId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/comments/add")
    @PreAuthorize("isAuthenticated()")
    public String addComment(@RequestParam("postId") Long postId,
                             @RequestParam("content") String content,
                             Principal principal) {
        if(content==null){
            return "redirect:";
        }
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setContent(content);
        commentDTO.setCreatedAt(LocalDateTime.now());
        commentDTO.setPost(postService.findPost(postId));
        commentDTO.setWriter(UserDTO.toUserDTO(userService.getUser(principal.getName())));

        commentService.createComment(commentDTO);
        return "redirect:/board/" + postId;
    }
}
