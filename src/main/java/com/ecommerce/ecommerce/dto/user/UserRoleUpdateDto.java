package com.ecommerce.ecommerce.dto.user;

import com.ecommerce.ecommerce.domain.enums.RoleEnumUser;

import jakarta.validation.constraints.NotNull;

public record UserRoleUpdateDto(
    @NotNull(message = "El ID del usuario no puede ser nulo.")
    Long userId,
    @NotNull(message = "El rol no puede ser nulo.")
    RoleEnumUser role
) {

}
