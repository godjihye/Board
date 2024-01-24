package com.jhshin.board.service;

import com.jhshin.board.entity.CommentEntity;
import com.jhshin.board.entity.PostEntity;
import com.jhshin.board.entity.UserEntity;
import com.jhshin.board.model.CommentDTO;
import com.jhshin.board.repository.CommentRepository;
import com.jhshin.board.repository.PostRepository;
import com.jhshin.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    @Transactional
    public void createComment(CommentDTO commentDTO) {
        Optional<UserEntity> _userEntity = userRepository.findByLoginId(commentDTO.getWriter().getLoginId());
        if(_userEntity.isEmpty()){
            throw new IllegalStateException("사용자가 존재하지 않습니다.");
        }
        UserEntity userEntity = _userEntity.get();

        Optional<PostEntity> _postEntity = postRepository.findById(commentDTO.getPost().getId());
        if(_postEntity.isEmpty()){
            throw new IllegalStateException("글이 존재하지 않습니다.");
        }
        PostEntity postEntity = _postEntity.get();

        CommentEntity comment = new CommentEntity();
        comment.setId(commentDTO.getId());
        comment.setWriter(userEntity);
        comment.setPost(postEntity);
        comment.setContent(commentDTO.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        commentRepository.save(comment);
    }

    public List<CommentDTO> getCommentsByPost(Long postId) {
        List<CommentDTO> allComments = new ArrayList<>();
        List<CommentEntity> comments = commentRepository.findAllByPostId(postId);
        for(CommentEntity comm : comments) {
            allComments.add(CommentDTO.toCommentDTO(comm));
        }
        return allComments;
    }
}
