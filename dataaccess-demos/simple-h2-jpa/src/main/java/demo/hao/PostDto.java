package demo.hao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
class PostDto {
    private Long id;
    private String name;
    private String category;
}

