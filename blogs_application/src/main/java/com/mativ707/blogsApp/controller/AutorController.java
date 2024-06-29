package com.mativ707.blogsApp.controller;

import com.mativ707.blogsApp.model.Autor;
import com.mativ707.blogsApp.service.IAutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/autores")
@PreAuthorize("denyAll()")
public class AutorController {

    @Autowired
    private IAutorService autorService;

    @GetMapping
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<List<Autor>> getAllAutores(){
        List<Autor> listaAutores = autorService.getAllAutores();
        return ResponseEntity.ok(listaAutores);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('READ')")
    public ResponseEntity<Autor> getAutorById(@PathVariable Long id){
        Optional<Autor> autor = autorService.getAutorById(id);
        return autor.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Autor> createAutor(@RequestBody Autor autor) throws Exception {
        Autor newAutor = autorService.createAutor(autor);
        return ResponseEntity.ok(newAutor);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteAutor(@PathVariable Long id){
        autorService.deleteAutorById(id);
        return ResponseEntity.ok("Se ha eliminado autor correctamente con ID: " + id);
    }

}
