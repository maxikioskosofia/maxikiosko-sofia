package com.ecommerce.ecommerce.dto.redeem;

import jakarta.validation.constraints.NotNull;

public record RedeemCreateDto(
    @NotNull(message = "El user ID no puede ser nulo.")
    Long userId,
    @NotNull(message = "El prize ID no puede ser nulo.")
    Long prizeId,
    String description
) {

}
