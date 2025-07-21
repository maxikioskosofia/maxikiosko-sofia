package com.ecommerce.ecommerce.dto.prize;

public record PrizeDto(
    Long id,
    String name,
    String description,
    Integer pointsCost,
    Integer stock,
    Boolean active
) {

}
