package com.ecommerce.ecommerce.service.jwt;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

    String getToken(UserDetails user);

    String getEmailFromToken(String token);

    boolean isTokenValid(String token, UserDetails userDetails);

    String getRoleFromToken(String token);
}