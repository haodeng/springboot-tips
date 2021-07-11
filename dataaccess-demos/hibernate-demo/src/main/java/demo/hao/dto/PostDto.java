package demo.hao.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;
    private String name;
//    private Long categoryId;

    private UserDto user;
    private Set<CommentDto> comments;
}
