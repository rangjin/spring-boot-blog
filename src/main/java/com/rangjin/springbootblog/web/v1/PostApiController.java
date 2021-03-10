package com.rangjin.springbootblog.web.v1;

import com.rangjin.springbootblog.service.PostService;
import com.rangjin.springbootblog.web.dto.PostRequestDto;
import com.rangjin.springbootblog.web.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostApiController {

    private final PostService postService;

    @PostMapping("/post")
    public Long save(@RequestBody PostRequestDto dto) {
        return postService.create(dto);
    }

    @GetMapping("/post/{id}")
    public PostResponseDto findById(@PathVariable("id") Long id) {
        return postService.findById(id);
    }

    @GetMapping("/post")
    public List<PostResponseDto> findByStatus() {
        return postService.findByStatus();
    }

    @PutMapping("/post/{id}")
    public Long update(@PathVariable("id") Long id, @RequestBody PostRequestDto dto) {
        return postService.modify(id, dto);
    }

    @DeleteMapping("/post/{id}")
    public Long delete(@PathVariable("id") Long id) {
        return postService.delete(id);
    }

}
