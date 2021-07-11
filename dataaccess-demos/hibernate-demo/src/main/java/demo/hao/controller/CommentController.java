package demo.hao.controller;


import demo.hao.dto.CommentDto;
import demo.hao.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public List<CommentDto> findAll() {
        return this.commentService.findAll();
    }

    @GetMapping("/{id}")
    public CommentDto findById(@PathVariable Long id) {
        return this.commentService.findById(id);
    }

    @PostMapping
    public CommentDto create(@RequestBody CommentDto reviewDto) {
        return this.commentService.createDto(reviewDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.commentService.delete(id);
    }
}
