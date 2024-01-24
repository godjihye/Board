package com.jhshin.board.model;

import com.jhshin.board.entity.PostEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UserDTO writer;
    public UserDTO getUser() {
        return writer;
    }
    public void setWriter(UserDTO writer){
        this.writer = writer;
    }
    public static PostDTO toPostDTO(PostEntity postEntity){
        PostDTO postDTO = new PostDTO();
        postDTO.setId(postEntity.getId());
        postDTO.setTitle(postEntity.getTitle());
        postDTO.setContent(postEntity.getContent());
        postDTO.setCreatedAt(postEntity.getCreatedAt());
        postDTO.setUpdatedAt(postEntity.getUpdatedAt());
        postDTO.setWriter(UserDTO.toUserDTO(postEntity.getWriter()));
        return postDTO;
    }
}
