package com.mativ707.blogsApp.controller;

import com.mativ707.blogsApp.model.Post;
import com.mativ707.blogsApp.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
@PreAuthorize("denyAll()")
public class PostController {

    @Autowired
    private IPostService postService;

    @GetMapping
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<List<Post>> getAllPosts(){
        List<Post> listaDePosts = postService.getAllPosts();
        return ResponseEntity.ok(listaDePosts);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<Post> getPostById(@PathVariable Long id){
        Optional<Post> post = postService.getPostById(id);
        return post.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'AUTHOR')")
    public ResponseEntity<Post> createPost(@RequestBody Post post){
        Post newPost = postService.createPost(post);
        return ResponseEntity.ok(newPost);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'AUTHOR')")
    public ResponseEntity<String> deleteAutor(@PathVariable Long id){
        postService.deletePostById(id);
        return ResponseEntity.ok("Se ha eliminado autor correctamente con ID: " + id);
    }
}
