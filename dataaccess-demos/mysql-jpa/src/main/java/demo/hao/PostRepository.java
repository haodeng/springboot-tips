package demo.hao;

import org.springframework.data.repository.PagingAndSortingRepository;

interface PostRepository extends PagingAndSortingRepository<Post, String> {}
