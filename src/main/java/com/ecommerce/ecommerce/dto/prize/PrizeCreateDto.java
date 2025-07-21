package com.ecommerce.ecommerce.dto.prize;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PrizeCreateDto(
    @NotNull(message = "El nombre del premio no puede ser nulo.")
    @NotBlank(message = "El nombre del premio no puede estar vacío.")
    String name,
    String description,
    @NotNull(message = "El costo del premio no puede ser nulo.")
    @Min(value = 1, message = "El costo del premio tiene que ser mayor a 0.")
    Integer pointsCost,
    @NotNull(message = "El stock no puede ser nulo.")
    @Min(value = 0, message = "El stock no puede ser negativo.")
    Integer stock,
    @NotNull(message = "El estado del premio no puede ser nulo.")
    Boolean active
) {

}
