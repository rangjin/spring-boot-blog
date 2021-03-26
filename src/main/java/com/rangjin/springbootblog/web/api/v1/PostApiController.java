package com.rangjin.springbootblog.web.api.v1;

import com.rangjin.springbootblog.domain.PageRequest;
import com.rangjin.springbootblog.service.PostService;
import com.rangjin.springbootblog.web.dto.PostRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/post")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    @GetMapping("")
    public ResponseEntity<?> findAll(PageRequest pageRequest) {
        // 관리자 인증되지 않았을 때
        // return new ResponseEntity<>(postService.findByStatus(pageRequest), HttpStatus.OK);
        return new ResponseEntity<>(postService.findAll(pageRequest), HttpStatus.OK);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<?> findByCategory(@PathVariable("id") Long id, PageRequest pageRequest) {
        // 관리자 인증되지 않았을 때
        // return new ResponseEntity<>(postService.findByStatusAndCategory(id, pageRequest), HttpStatus.OK);
        return new ResponseEntity<>(postService.findByCategory(id, pageRequest), HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(postService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid PostRequestDto dto, Errors errors) {
        if (errors.hasErrors()) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(postService.create(dto), HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> modify(@PathVariable("id") Long id, @RequestBody @Valid PostRequestDto dto, Errors errors) {
        if (errors.hasErrors()) {
            return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(postService.modify(id, dto), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        postService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
