package demo.hao;

import org.springframework.beans.BeanUtils;

import java.util.Optional;

public class AppUtils {

    public static Optional<PostDto> daoToDto(Optional<Post> post) {
        PostDto postDto = new PostDto();
        BeanUtils.copyProperties(post, postDto);
        return Optional.of(postDto);
    }

    public static PostDto daoToDto(Post post) {
        PostDto postDto = new PostDto();
        BeanUtils.copyProperties(post, postDto);
        return postDto;
    }

    public static Post dtoToDao(PostDto postDto) {
        Post post = new Post();
        BeanUtils.copyProperties(postDto, post);
        return post;
    }
}
