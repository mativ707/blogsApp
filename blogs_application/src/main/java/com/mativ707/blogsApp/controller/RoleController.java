package com.mativ707.blogsApp.controller;

import com.mativ707.blogsApp.model.Permission;
import com.mativ707.blogsApp.model.Role;
import com.mativ707.blogsApp.service.IPermissionService;
import com.mativ707.blogsApp.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/roles")
@PreAuthorize("denyAll()")
public class RoleController {

    @Autowired
    private IRoleService roleService;
    @Autowired
    private IPermissionService permissionService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.findAll();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Role> getRoleById(@PathVariable Long id) {
        Optional<Role> role = roleService.findById(id);
        return role.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Role> createRole(@RequestBody Role role) {
        Set<Permission> permissionList = new HashSet<>();
        Permission readPermission;

        for (Permission per : role.getPermissionsList()) {
            readPermission = permissionService.findById(per.getId()).orElse(null);
            if (readPermission != null) {
                permissionList.add(readPermission);
            }
        }
        role.setPermissionsList(permissionList);
        Role newRole = roleService.save(role);
        return ResponseEntity.ok(role);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Role> updateRolePermission(@PathVariable Long id, @RequestBody Set<Long> idPermission){
        Optional<Role> role = roleService.findById(id);

        if(role.isPresent()){
            Role updatedRole = role.get();
            Set<Permission> updatedPermission = new HashSet<>();
            Permission readPermission;

            for (Long per : idPermission) {
                readPermission = permissionService.findById(per).orElse(null);
                if (readPermission != null) {
                    updatedPermission.add(readPermission);
                }
            }

            updatedRole.setPermissionsList(updatedPermission);
            updatedRole = roleService.update(updatedRole);
            return ResponseEntity.ok(updatedRole);
        }

        return ResponseEntity.notFound().build();
    }
}