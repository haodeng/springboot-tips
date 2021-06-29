package demo.hao;

import lombok.*;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
@Data
@NoArgsConstructor
@RequiredArgsConstructor
class Post {
    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String name;
}

