package com.mativ707.blogsApp.service;

import com.mativ707.blogsApp.model.Post;

import java.util.List;
import java.util.Optional;

public interface IPostService {
    Post createPost(Post post);
    List<Post> getAllPosts();
    Optional<Post> getPostById(Long id);
    void deletePostById(Long id);
    Post updatePost(Post updatedPost);
}
