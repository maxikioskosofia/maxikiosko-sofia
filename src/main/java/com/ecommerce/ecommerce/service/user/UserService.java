package com.ecommerce.ecommerce.service.user;

import java.util.List;

import com.ecommerce.ecommerce.domain.User;
import com.ecommerce.ecommerce.dto.user.UserRegisterDto;
import com.ecommerce.ecommerce.dto.user.UserRoleUpdateDto;

public interface UserService {
    User createUser(UserRegisterDto userRegisterDto);
    User getLoggingUser();
    User getUserById(Long id_user);

    void updateUserRole(UserRoleUpdateDto userRoleUpdateDto);

    List<User> getAllUsers();
}