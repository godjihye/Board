package com.jhshin.board.entity;

import com.jhshin.board.model.CommentDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="comment_tbl")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn
    private PostEntity post;

    @ManyToOne
    @JoinColumn
    private UserEntity writer;

    private LocalDateTime createdAt;

    public static CommentEntity toCommentEntity(CommentDTO commentDTO) {
        CommentEntity entity = new CommentEntity();
        entity.setId(commentDTO.getId());
        entity.setContent(commentDTO.getContent());
        entity.setCreatedAt(commentDTO.getCreatedAt());
        entity.setPost(PostEntity.toPostEntity(commentDTO.getPost()));
        entity.setWriter(UserEntity.toUserEntity(commentDTO.getUser()));
        return entity;
    }
}
