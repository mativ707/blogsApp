package com.mativ707.blogsApp.service;

import com.mativ707.blogsApp.model.Role;

import java.util.List;
import java.util.Optional;

public interface IRoleService {
    List<Role> findAll();
    Optional<Role> findById(Long id);
    Role save(Role role);
    void deleteBYId(Long id);
    Role update(Role role);
}
