package com.mativ707.blogsApp.service;

import com.mativ707.blogsApp.model.Role;
import com.mativ707.blogsApp.model.UserSec;
import com.mativ707.blogsApp.repository.IUserSecRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService implements IUserService{
    @Autowired
    private IUserSecRepository userRepository;

    @Autowired
    private IRoleService roleService;

    @Override
    public List<UserSec> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<UserSec> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public UserSec save(UserSec userSec) {
        return userRepository.save(userSec);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserSec update(UserSec updatedUser, Long id) throws Exception {
        Optional<UserSec> userOptional = this.findById(id);

        if (userOptional.isEmpty()) {
            throw new Exception("User not found");
        }

        UserSec existingUser = userOptional.get();
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setPassword(this.encriptPassword(updatedUser.getPassword()));
        existingUser.setAccountNotExpired(updatedUser.isAccountNotExpired());
        existingUser.setCredentialNotExpired(updatedUser.isCredentialNotExpired());
        existingUser.setAccountNotLocked(updatedUser.isAccountNotLocked());

        if(updatedUser.getRolesList() == null || updatedUser.getRolesList().isEmpty()){
            throw new Exception("Roles List is empty");
        }

        Set<Role> roleList = new HashSet<>();
        Role readRole;

        //Recuperar permissions por su ID
        for (Role role : updatedUser.getRolesList()) {
            readRole = roleService.findById(role.getId()).orElseThrow(() -> new RuntimeException("No se ha encontrado el rol"));
            if (readRole != null) {
                roleList.add(readRole);
            }
        }

        existingUser.setRolesList(roleList);

        return userRepository.save(existingUser);
    }

    @Override
    public String encriptPassword(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
