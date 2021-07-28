package demo.hao;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

interface PostRepository extends JpaRepository<Post, String> {

    Iterable<Post> findByName(String name);
    Iterable<Post> findByNameIgnoreCase(String name);
    Iterable<Post> findByName(String name, Sort sort);

    Iterable<Post> findByNameContaining(String partialName);
    Iterable<Post> findByNameIgnoreCaseContaining(String partialName);
    Iterable<Post> findByNameContaining(String partialName, Sort sort);
}
