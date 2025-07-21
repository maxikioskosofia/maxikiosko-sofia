package com.ecommerce.ecommerce.dto.redeem;

import java.time.LocalDateTime;

import com.ecommerce.ecommerce.domain.enums.StatusEnumRedeem;

public record RedeemDto(
    Long id,
    Long userId,
    String userName,
    Long prizeId,
    String prizeName,
    Integer pointsUsed,
    LocalDateTime redeemDate,
    StatusEnumRedeem status,
    String description
) {

}
