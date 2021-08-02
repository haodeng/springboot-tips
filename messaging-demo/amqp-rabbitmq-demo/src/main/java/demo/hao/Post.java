package demo.hao;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
class Post {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public Post(String name) {
        this.name = name;
    }
}

