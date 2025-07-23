package com.ecommerce.ecommerce.dto.prize;

import jakarta.validation.constraints.NotNull;

public record PrizeActiveDto(
    @NotNull(message = "El estado del premio no puede ser nulo.")
    Boolean active
) {

}
