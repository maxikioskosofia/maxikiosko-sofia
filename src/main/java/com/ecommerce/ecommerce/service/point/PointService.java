package com.ecommerce.ecommerce.service.point;

import java.util.List;

import com.ecommerce.ecommerce.dto.point.PointAssigmentDto;
import com.ecommerce.ecommerce.dto.point.PointDto;
import com.ecommerce.ecommerce.dto.point.PointUpdateDto;

public interface PointService {
    PointDto assignPoints(PointAssigmentDto pointAssignDto);
    List<PointDto> getPointsByUser(Long userId);
    List<PointDto> getAllPoints();
    PointDto updatePoints(Long pointId, PointUpdateDto pointUpdateDto);
    void deletePoints(Long pointId);
}
