package demo.hao.dao;

import demo.hao.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "SELECT p.* FROM posts p inner " +
            "join users u on p.user_id = u.id " +
            "where u.email = :email",
            nativeQuery = true)
    Collection<Post> findPostsByUserEmail(@Param("email") String email);
}
