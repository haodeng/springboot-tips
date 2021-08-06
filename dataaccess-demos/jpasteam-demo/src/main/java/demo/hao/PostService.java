package demo.hao;

import com.speedment.jpastreamer.application.JPAStreamer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final JPAStreamer jpaStreamer;

    public List<Post> getAllPosts() {
        return jpaStreamer.stream(Post.class)
                .collect(Collectors.toList());
    }

    public List<Post> getPostsByPage(int offset, int limit) {
        return jpaStreamer.stream(Post.class)
                .sorted(Post$.name)
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList());
    }

    public Optional<Post> getPostById(Long id) {
        return jpaStreamer.stream(Post.class)
                .filter(Post$.id.equal(id))
                .findFirst();
    }

    public Map<String, List<Post>> getPostGroupByCategory() {
        return jpaStreamer.stream(Post.class)
                .collect(Collectors.groupingBy(Post::getCategory));
    }
}
