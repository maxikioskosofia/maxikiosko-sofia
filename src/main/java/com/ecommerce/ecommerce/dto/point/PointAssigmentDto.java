package com.ecommerce.ecommerce.dto.point;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PointAssigmentDto(
    @NotNull(message = "El user ID no puede ser nulo.")
    Long userId,
    @NotNull(message = "Los puntos no pueden ser nulos.")
    @Min(value = 1, message = "Los puntos deben ser mayores a 0.")
    Integer points
) {

}
