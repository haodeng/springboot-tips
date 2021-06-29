package demo.hao.dao.ds1;

import demo.hao.model.ds1.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {}
