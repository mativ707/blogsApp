package com.mativ707.blogsApp.controller;

import com.mativ707.blogsApp.model.Role;
import com.mativ707.blogsApp.model.UserSec;
import com.mativ707.blogsApp.service.IRoleService;
import com.mativ707.blogsApp.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("denyAll()")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserSec>> getAllUsers() {
        List<UserSec> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserSec> findById(Long id) {
        Optional<UserSec> user = userService.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserSec> createUser(@RequestBody UserSec userSec) {
        Set<Role> roleList = new HashSet<>();
        Role readRole;

        userSec.setPassword(userService.encriptPassword(userSec.getPassword()));

        for (Role role : userSec.getRolesList()) {
            readRole = roleService.findById(role.getId()).orElseThrow(() -> new RuntimeException("No se ha encontrado el rol"));
            if (readRole != null) {
                roleList.add(readRole);
            }
        }
        userSec.setRolesList(roleList);

        UserSec newUser = userService.save(userSec);
        return ResponseEntity.ok(newUser);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserSec> updateUser(@PathVariable Long id, @RequestBody UserSec updatedUser)
            throws Exception {

        UserSec newUser = userService.update(updatedUser, id);

        return ResponseEntity.ok(newUser);
    }
}
