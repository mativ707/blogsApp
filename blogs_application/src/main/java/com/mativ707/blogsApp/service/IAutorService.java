package com.mativ707.blogsApp.service;

import com.mativ707.blogsApp.model.Autor;

import java.util.List;
import java.util.Optional;

public interface IAutorService {
    Autor createAutor(Autor autor) throws Exception;
    List<Autor> getAllAutores();
    Optional<Autor> getAutorById(Long id);
    void deleteAutorById(Long id);
    Autor updateAutor(Autor updatedAutor);
}
