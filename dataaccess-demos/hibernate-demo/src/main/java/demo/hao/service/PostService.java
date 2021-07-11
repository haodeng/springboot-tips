package demo.hao.service;

import demo.hao.dao.CategoryRepository;
import demo.hao.dao.PostRepository;
import demo.hao.dto.PostDto;
import demo.hao.model.Comment;
import demo.hao.model.Post;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {
    private final PostRepository postRepository;

    public static PostDto mapToDto(Post post) {
        if (post != null) {
            return new PostDto(
                    post.getId(),
                    post.getName(),
                    post.getCategory().getId(),
                    UserService.mapToDto(post.getUser()),
                    post.getComments().stream().map(CommentService::mapToDto).collect(Collectors.toSet())
            );
        }
        return null;
    }

    public List<PostDto> findAll() {
        log.debug("Request to get all Posts");
        return this.postRepository.findAll()
                .stream()
                .map(PostService::mapToDto)
                .collect(Collectors.toList());
    }

    public List<PostDto> findPostsByUserEmail(String email) {
        log.debug("Request to findPostsByUserEmail");
        return this.postRepository.findPostsByUserEmail(email)
                .stream()
                .map(PostService::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PostDto findById(Long id) {
        log.debug("Request to get Post : {}", id);
        return this.postRepository.findById(id).map(PostService::mapToDto).orElse(null);
    }

    public PostDto create(PostDto postDto) {
        log.debug("Request to create Post : {}", postDto);

        return mapToDto(this.postRepository.save(
                new Post(postDto.getName())));
    }

    public PostDto addComment(Long productId, Comment comment) {
        Post post = this.postRepository
                .findById(productId)
                .orElseThrow(() -> new IllegalStateException("The Product does not exist!"));
        post.getComments().add(comment);
        this.postRepository.saveAndFlush(post);
        return mapToDto(post);
    }

    public void delete(Long id) {
        log.debug("Request to delete post : {}", id);
        this.postRepository.deleteById(id);
    }
}
