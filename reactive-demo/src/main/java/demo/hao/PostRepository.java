package demo.hao;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

interface PostRepository extends ReactiveCrudRepository<Post, Long> {}
