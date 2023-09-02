package com.blopapi.service.impl;

import com.blopapi.entity.Comment;
import com.blopapi.entity.Post;
import com.blopapi.exception.ResourceNotFound;
import com.blopapi.payload.CommentDto;
import com.blopapi.repository.CommentRepository;
import com.blopapi.repository.PostRepository;
import com.blopapi.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private PostRepository postRepo;
    private CommentRepository commentRepo;

    public CommentServiceImpl(PostRepository postRepo, CommentRepository CommentRepo) {
        this.postRepo = postRepo;
        this.commentRepo = CommentRepo;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFound(postId)


        );

        Comment comment = new Comment();
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        comment.setPost(post);

        Comment savedComment = commentRepo.save(comment);

        CommentDto dto = new CommentDto();
        dto.setId(savedComment.getId());
        dto.setName(savedComment.getName());
        dto.setEmail(savedComment.getEmail());
        dto.setBody(savedComment.getBody());
        return dto;
    }

    public List<CommentDto> findCommentByPostId(long postId) {

        postRepo.findById(postId).orElseThrow(

                ()->new ResourceNotFound(postId)
        );
        List<Comment> comments = commentRepo.findByPostId(postId);
       return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());


    }

    @Override
    public void deleteCommentById(long postId, long id) {
        Post post = postRepo.findById(postId).orElseThrow(

                () -> new ResourceNotFound(postId)
        );
            Comment comment = commentRepo.findById(id).orElseThrow(

                () -> new ResourceNotFound(id)
        );
        commentRepo.deleteById(id);


    }

    @Override
    public CommentDto getCommentById(long postId, long id) {
        Post post = postRepo.findById(postId).orElseThrow(

                () -> new ResourceNotFound(postId)
        );
        Comment comment = commentRepo.findById(id).orElseThrow(

                () -> new ResourceNotFound(id)
        );
        CommentDto dto = mapToDto(comment);
        return dto;
    }

    @Override
    public CommentDto updateCommentById(long postId, long commentId, CommentDto commentDto) {
        // retrieve post entity by id
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFound( postId));

        // retrieve comment by id
        Comment comment = commentRepo.findById(commentId).orElseThrow(
                () -> new ResourceNotFound(commentId));



        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatedComment = commentRepo.save(comment);
        return mapToDto(updatedComment);
    }


    //Step 3: Update CommentServiceImpl class:



    CommentDto mapToDto(Comment comment){ // Here you can user Mapper class
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setName(comment.getName());
        dto.setEmail(comment.getEmail());
        dto.setBody(comment.getBody());
        return dto;
    }
    }
