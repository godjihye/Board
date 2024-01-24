package com.jhshin.board.model;

import com.jhshin.board.entity.CommentEntity;
import com.jhshin.board.entity.PostEntity;
import com.jhshin.board.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommentDTO {
    private Long id;
    private String content;
    private PostDTO post;
    private UserDTO writer;
    private LocalDateTime createdAt;
    public UserDTO getUser() {
        return writer;
    }
    public void setWriter(UserDTO writer){
        this.writer = writer;
    }
    public PostDTO getPost() {return post;}
    public void setPost() {this.post = post;}

    public static CommentDTO toCommentDTO(CommentEntity commentEntity) {
        CommentDTO dto = new CommentDTO();
        dto.setId(commentEntity.getId());
        dto.setContent(commentEntity.getContent());
        dto.setPost(PostDTO.toPostDTO(commentEntity.getPost()));
        dto.setWriter(UserDTO.toUserDTO(commentEntity.getWriter()));
        dto.setCreatedAt(commentEntity.getCreatedAt());
        return dto;
    }
}
