package com.ecommerce.ecommerce.service.user;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.domain.User;
import com.ecommerce.ecommerce.domain.enums.RoleEnumUser;
import com.ecommerce.ecommerce.dto.user.UserRegisterDto;
import com.ecommerce.ecommerce.dto.user.UserRoleUpdateDto;
import com.ecommerce.ecommerce.mappers.user.UserMapper;
import com.ecommerce.ecommerce.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserMapper userMapper;

    @Override
    public User createUser(UserRegisterDto userRegisterDto) {
        User userCreated = userMapper.userRegisterDtoToUser(userRegisterDto);
        userCreated.setPassword(passwordEncoder.encode(userRegisterDto.password()));
        
        // Asignar rol ADMIN al primer usuario, USER a los demás
        if (userRepository.count() == 0) {
            userCreated.setRole(RoleEnumUser.ADMIN);
        } else {
            userCreated.setRole(RoleEnumUser.USER);
        }
        
        return userRepository.save(userCreated);
    }

    @Override
    public User getLoggingUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UsernameNotFoundException("No se ha encontrado ningún usuario autenticado.");
        }
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario con email: " + email + " no encontrado."));
    }

    @Override
    public User getUserById(Long id_user) {
        return userRepository.findById(id_user)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario con ID: " + id_user + " no encontrado."));
    }

    @Override
    @Transactional
    public void updateUserRole(UserRoleUpdateDto userRoleUpdateDto) {
        User user = userRepository.findById(userRoleUpdateDto.userId())
                .orElseThrow(() -> new UsernameNotFoundException("Usuario con ID: " + userRoleUpdateDto.userId() + " no encontrado."));
        
        // Opcional: Evitar que un ADMIN se degrade a sí mismo si es el único ADMIN
        if (user.getRole() == RoleEnumUser.ADMIN && userRepository.countByRole(RoleEnumUser.ADMIN) == 1) {
            throw new IllegalStateException("No se puede modificar el rol del único administrador.");
        }
        
        user.setRole(userRoleUpdateDto.role());
        userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}