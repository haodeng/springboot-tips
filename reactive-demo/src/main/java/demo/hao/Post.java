package demo.hao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;


@Data
@NoArgsConstructor
@AllArgsConstructor
class Post {
    @Id
    private Long id;
    private String name;

    public Post(String name) {
        this(null, name);
    }
}

