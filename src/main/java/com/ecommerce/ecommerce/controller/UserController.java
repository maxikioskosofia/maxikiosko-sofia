package com.ecommerce.ecommerce.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.dto.user.UserDto;
import com.ecommerce.ecommerce.dto.user.UserRoleUpdateDto;
import com.ecommerce.ecommerce.mappers.user.UserMapper;
import com.ecommerce.ecommerce.service.user.UserService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;
    private UserMapper userMapper;

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") Long id_user) {
        UserDto userDto=userMapper.userToUserDto(userService.getUserById(id_user));
        return ResponseEntity.ok(userDto);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser() {
        UserDto userDto = userMapper.userToUserDto(userService.getLoggingUser());
        return ResponseEntity.ok(userDto);
    }
    
    @PatchMapping("/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateUserRole(@RequestBody @Valid UserRoleUpdateDto userRoleUpdateDto) {
        userService.updateUserRole(userRoleUpdateDto);
        return ResponseEntity.ok("Rol actualizado correctamente.");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers().stream()
            .map(userMapper::userToUserDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }
}