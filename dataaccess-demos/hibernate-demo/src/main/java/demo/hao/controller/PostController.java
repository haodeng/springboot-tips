package demo.hao.controller;


import demo.hao.dto.PostDto;
import demo.hao.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/posts")
class PostController {
    private final PostService postService;

    @GetMapping
    public List<PostDto> findAll() {
        return this.postService.findAll();
    }

    @GetMapping("/search")
    public List<PostDto> findPostsByUserEmail(@RequestParam String email) {
        return this.postService.findPostsByUserEmail(email);
    }

    @GetMapping("/{id}")
    public PostDto findById(@PathVariable Long id) {
        return this.postService.findById(id);
    }

    @PostMapping
    public PostDto create(@RequestBody PostDto postDto) {
        return this.postService.create(postDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        this.postService.delete(id);
    }
}

