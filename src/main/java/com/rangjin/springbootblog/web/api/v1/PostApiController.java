package com.rangjin.springbootblog.web.api.v1;

import com.rangjin.springbootblog.domain.PageRequest;
import com.rangjin.springbootblog.service.PostService;
import com.rangjin.springbootblog.web.dto.FormResponse;
import com.rangjin.springbootblog.web.dto.PostRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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
        if (SecurityContextHolder.getContext().getAuthentication().getClass() == UsernamePasswordAuthenticationToken.class) {
            return new ResponseEntity<>(postService.findAll(pageRequest), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(postService.findByStatus(pageRequest), HttpStatus.OK);
        }
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<?> findByCategory(@PathVariable("id") Long id, PageRequest pageRequest) {
        if (SecurityContextHolder.getContext().getAuthentication().getClass() == UsernamePasswordAuthenticationToken.class) {
            return new ResponseEntity<>(postService.findByCategory(id, pageRequest), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(postService.findByStatusAndCategory(id, pageRequest), HttpStatus.OK);
        }
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(postService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid PostRequestDto dto, Errors errors) {
        if (errors.hasErrors()) {
            return new ResponseEntity<>(new FormResponse<>(false, errors), HttpStatus.OK);
        }

        return new ResponseEntity<>(new FormResponse<>(true, postService.create(dto)), HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> modify(@PathVariable("id") Long id,
                                    @RequestBody @Valid PostRequestDto dto, Errors errors) {
        if (errors.hasErrors()) {
            return new ResponseEntity<>(new FormResponse<>(false, errors), HttpStatus.OK);
        }

        return new ResponseEntity<>(new FormResponse<>(true, postService.modify(id, dto)), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        postService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
