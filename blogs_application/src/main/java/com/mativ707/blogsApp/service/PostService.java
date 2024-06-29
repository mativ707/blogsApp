package com.mativ707.blogsApp.service;

import com.mativ707.blogsApp.model.Autor;
import com.mativ707.blogsApp.model.Post;
import com.mativ707.blogsApp.repository.IAutorRepository;
import com.mativ707.blogsApp.repository.IPostRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PostService implements IPostService{
    @Autowired
    private IPostRepository postRepository;
    @Autowired
    private IAutorRepository autorService;

    @Override
    public Post createPost(Post post) {
        Autor autor = autorService.findById(post.getUnAutor().getId())
                .orElseThrow(() -> new EntityNotFoundException("Autor not found with ID: " + post.getUnAutor().getId()));
        post.setUnAutor(autor);
        return postRepository.save(post);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Optional<Post> getPostById(Long id) {
        return postRepository.findById(id);
    }

    @Override
    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public Post updatePost(Post updatedPost) {
        return postRepository.save(updatedPost);
    }
}
