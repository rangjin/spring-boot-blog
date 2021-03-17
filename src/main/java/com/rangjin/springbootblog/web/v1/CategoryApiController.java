package com.rangjin.springbootblog.web.v1;

import com.rangjin.springbootblog.service.CategoryService;
import com.rangjin.springbootblog.web.dto.CategoryRequestDto;
import com.rangjin.springbootblog.web.dto.CategoryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CategoryApiController {

    private final CategoryService categoryService;

    @PostMapping("/category")
    public Long save(@RequestBody CategoryRequestDto dto) {
        return categoryService.create(dto);
    }

    @GetMapping("/category/{id}")
    public CategoryResponseDto findById(@PathVariable("id") Long id) {
        return categoryService.findById(id);
    }

    @PutMapping("/category/{id}")
    public Long update(@PathVariable("id") Long id, @RequestBody CategoryRequestDto dto) {
        return categoryService.modify(id, dto);
    }

    @DeleteMapping("/category/{id}")
    public void delete(@PathVariable("id") Long id) {
        categoryService.delete(id);
    }

}
