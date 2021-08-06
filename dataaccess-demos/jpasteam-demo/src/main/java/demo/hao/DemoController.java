package demo.hao;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
class DemoController {
    private final PostService postService;

    @GetMapping
    List<Post> getPosts() {
        return postService.getAllPosts();
    }

    @GetMapping("/page")
    List<Post> getPostsByPage(@RequestParam int offset, @RequestParam int limit) {
        return postService.getPostsByPage(offset, limit);
    }

    @GetMapping("/{id}")
    Optional<Post> getPostById(@PathVariable Long id) {
        return postService.getPostById(id);
    }

    @GetMapping("/group_by_category")
    public Map<String, List<Post>> getPostGroupByCategory(){
        return postService.getPostGroupByCategory();
    }
}

