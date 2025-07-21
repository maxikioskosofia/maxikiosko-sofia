package com.ecommerce.ecommerce.mappers.point;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ecommerce.ecommerce.domain.Point;
import com.ecommerce.ecommerce.dto.point.PointDto;

@Mapper(componentModel = "spring")
public interface PointMapper {
    @Mapping(target = "userId", source = "user.id_user")
    @Mapping(target = "userName", source = "user.name")
    @Mapping(target = "assignedById", source = "assignedBy.id_user")
    @Mapping(target = "assignedByName", source = "assignedBy.name")
    PointDto pointToPointDto(Point point);
}
