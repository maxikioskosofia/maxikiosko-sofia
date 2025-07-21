package com.ecommerce.ecommerce.dto.point;

import java.time.LocalDateTime;

public record PointDto(
    Long id_punto,
    Long userId,
    String userName,
    Integer points,
    Long assignedById,
    String assignedByName,
    LocalDateTime assignmentDate,
    String description
) {

}
