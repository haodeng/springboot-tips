package demo.hao;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByName(String name);
    List<Post> findByNameIgnoreCase(String name);
    List<Post> findByName(String name, Sort sort);

    List<Post> findByNameContaining(String partialName);
    List<Post> findByNameIgnoreCaseContaining(String partialName);
    List<Post> findByNameContaining(String partialName, Sort sort);

    @Query("select p from Post p where p.name = ?1")
    Optional<Post> findByNameQuery(String name);

    @Query(value = "SELECT * FROM POST WHERE NAME = ?1", nativeQuery = true)
    Optional<Post> findByNameNativeQuery(String name);

    @Transactional
    @Modifying
    @Query("update Post p set p.name = ?1 where p.id = ?2")
    int setPostNameById(String name, Long postId);
}
