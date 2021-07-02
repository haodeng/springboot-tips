package demo.hao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
class Post {
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public Post(String name) {
        this.name = name;
    }
}

