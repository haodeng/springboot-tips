package demo.hao;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByName(String name);
    List<Post> findByNameIgnoreCase(String name);
    List<Post> findByName(String name, Sort sort);

    List<Post> findByNameContaining(String partialName);
    List<Post> findByNameIgnoreCaseContaining(String partialName);
    List<Post> findByNameContaining(String partialName, Sort sort);
}
