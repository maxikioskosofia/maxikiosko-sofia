package com.ecommerce.ecommerce.service.auth;

import com.ecommerce.ecommerce.dto.auth.AuthResponseDto;
import com.ecommerce.ecommerce.dto.user.UserLoginDto;
import com.ecommerce.ecommerce.dto.user.UserRegisterDto;

public interface AuthService {
    AuthResponseDto register(UserRegisterDto userRegisterDto);
    AuthResponseDto login(UserLoginDto userLoginDto);

    String getCurrentUserEmail();
}