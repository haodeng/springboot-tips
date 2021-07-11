package demo.hao.service;

import demo.hao.dao.CommentRepository;
import demo.hao.dto.CommentDto;
import demo.hao.model.Comment;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class CommentService {
 
    private final CommentRepository commentRepository;
    

    public static CommentDto mapToDto(Comment comment) {
        if (comment != null) {
            return new CommentDto(
                    comment.getId(),
                    comment.getDescription()
            );
        }
        return null;
    }

    public List<CommentDto> findAll() {
        log.debug("Request to get all Reviews");
        return this.commentRepository.findAll()
                .stream()
                .map(CommentService::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CommentDto findById(Long id) {
        log.debug("Request to get Review : {}", id);
        return this.commentRepository.findById(id).map(CommentService::mapToDto).orElse(null);
    }

    public CommentDto createDto(CommentDto CommentDto) {
        log.debug("Request to create Review : {}", CommentDto);
        return mapToDto(create(CommentDto));
    }

    public Comment create(CommentDto commentDto) {
        return this.commentRepository.save(
                new Comment(commentDto.getDescription())
        );
    }

    public void delete(Long id) {
        log.debug("Request to delete Review : {}", id);
        this.commentRepository.deleteById(id);
    }
}