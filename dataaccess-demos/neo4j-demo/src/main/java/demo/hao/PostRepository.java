package demo.hao;

import org.springframework.data.neo4j.repository.Neo4jRepository;

interface PostRepository extends Neo4jRepository<Post, Long> {}
