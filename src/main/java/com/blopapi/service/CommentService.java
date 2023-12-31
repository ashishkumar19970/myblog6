package com.blopapi.service;

import com.blopapi.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
    public List<CommentDto> findCommentByPostId(long postId);
    void deleteCommentById(long postId, long id);

    CommentDto getCommentById(long postId, long id);

    CommentDto updateCommentById(long postId, long commentId, CommentDto commentDto);

    //Step 3: Update CommentServiceImpl class:

}
