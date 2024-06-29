package com.mativ707.blogsApp.service;

import com.mativ707.blogsApp.model.UserSec;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<UserSec> findAll();
    Optional<UserSec> findById(Long id);
    UserSec save(UserSec userSec);
    void deleteById(Long id);
    UserSec update(UserSec updatedUser, Long id) throws Exception;
    String encriptPassword(String password);
}
