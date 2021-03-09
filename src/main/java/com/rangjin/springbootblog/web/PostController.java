package com.rangjin.springbootblog.web;

import com.rangjin.springbootblog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

}
