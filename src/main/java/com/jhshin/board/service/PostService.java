package com.jhshin.board.service;

import com.jhshin.board.entity.PostEntity;
import com.jhshin.board.model.PostDTO;
import com.jhshin.board.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    public void add(PostDTO postDTO) {
        PostEntity addPost = PostEntity.toPostEntity(postDTO);
        postRepository.save(addPost);
    }

    public List<PostDTO> findAll() {
        List<PostEntity> postEntityList = postRepository.findAll();
        List<PostDTO> allPosts = new ArrayList<>();
        for (PostEntity post : postEntityList) {
            allPosts.add(PostDTO.toPostDTO(post));
        }
        return allPosts;
    }
}
