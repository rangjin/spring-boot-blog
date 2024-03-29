package com.rangjin.springbootblog.web.api.v1;

import com.rangjin.springbootblog.domain.validator.CategoryValidator;
import com.rangjin.springbootblog.service.CategoryService;
import com.rangjin.springbootblog.web.dto.CategoryRequestDto;
import com.rangjin.springbootblog.web.dto.FormResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryApiController {

    private final CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<?> categoryList() {
        return new ResponseEntity<>(categoryService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(categoryService.findById(id), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody @Valid CategoryRequestDto dto, Errors errors) {
        new CategoryValidator(categoryService).validate(dto, errors);

        if (errors.hasErrors()) {
            return new ResponseEntity<>(new FormResponse<>(false, errors), HttpStatus.OK);
        }

        return new ResponseEntity<>(new FormResponse<>(true, categoryService.create(dto)), HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> modify(@PathVariable("id") Long id,
                                    @RequestBody @Valid CategoryRequestDto dto, Errors errors) {
        new CategoryValidator(categoryService).validate(dto, errors);

        if (errors.hasErrors()) {
            return new ResponseEntity<>(new FormResponse<>(false, errors), HttpStatus.OK);
        }

        return new ResponseEntity<>(new FormResponse<>(true, categoryService.modify(id, dto)), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        categoryService.delete(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
