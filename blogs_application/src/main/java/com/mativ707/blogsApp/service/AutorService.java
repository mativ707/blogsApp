package com.mativ707.blogsApp.service;

import com.mativ707.blogsApp.model.Autor;
import com.mativ707.blogsApp.model.Post;
import com.mativ707.blogsApp.model.UserSec;
import com.mativ707.blogsApp.repository.IAutorRepository;
import com.mativ707.blogsApp.repository.IPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class AutorService implements IAutorService{
    @Autowired
    private IAutorRepository autorRepo;
    @Autowired
    private IUserService userService;
    @Autowired
    private IPostRepository postService;

    @Override
    public Autor createAutor(Autor autor) throws Exception {
        UserSec usuario = userService.findById(autor.getUsuario().getId()).orElse(null);
        if(usuario == null){
            throw new Exception("User not found with ID: " + autor.getUsuario().getId());
        }
        autor.setUsuario(usuario);

        if(!autor.getListaDePosts().isEmpty()){
            Set<Post> listaDePosts = new HashSet<Post>();
            Post readPost;

            for(Post post : autor.getListaDePosts()){
                readPost = postService.findById(post.getId()).orElse(null);
                if(readPost != null){
                    listaDePosts.add(post);
                }
            }

            autor.setListaDePosts(listaDePosts);
        }

        return autorRepo.save(autor);
    }

    @Override
    public List<Autor> getAllAutores() {
        return autorRepo.findAll();
    }

    @Override
    public Optional<Autor> getAutorById(Long id) {
        return autorRepo.findById(id);
    }

    @Override
    public void deleteAutorById(Long id) {
        autorRepo.deleteById(id);
    }

    @Override
    public Autor updateAutor(Autor updatedAutor) {
        return autorRepo.save(updatedAutor);
    }
}
