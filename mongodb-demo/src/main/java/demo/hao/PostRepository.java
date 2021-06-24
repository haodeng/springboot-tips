package demo.hao;

import org.springframework.data.mongodb.repository.MongoRepository;

interface PostRepository extends MongoRepository<Post, String> {}
