package com.rangjin.springbootblog.service;

import com.rangjin.springbootblog.domain.post.Post;
import com.rangjin.springbootblog.domain.post.PostRepository;
import com.rangjin.springbootblog.web.dto.PostRequestDto;
import com.rangjin.springbootblog.web.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Long create(PostRequestDto dto) {
        Post post = Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .status(dto.getStatus())
                .build();

        return postRepository.save(post).getId();
    }

    public PostResponseDto findById(Long id) {
        return new PostResponseDto(postRepository.findById(id).orElseThrow(RuntimeException::new));
    }

    public Long modify(Long id, PostRequestDto dto) {
        Post post = postRepository.findById(id)
                .orElseThrow(RuntimeException::new);

        post.update(dto);

        postRepository.save(post);

        return post.getId();
    }

    public Long delete(Long id) {
        postRepository.deleteById(id);

        return id;
    }

}
