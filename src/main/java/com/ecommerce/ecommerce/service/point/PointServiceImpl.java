package com.ecommerce.ecommerce.service.point;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.domain.Point;
import com.ecommerce.ecommerce.domain.User;
import com.ecommerce.ecommerce.dto.point.PointAssigmentDto;
import com.ecommerce.ecommerce.dto.point.PointDto;
import com.ecommerce.ecommerce.mappers.point.PointMapper;
import com.ecommerce.ecommerce.repository.PointRepository;
import com.ecommerce.ecommerce.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PointServiceImpl implements PointService{
    private PointRepository pointRepository;
    private UserRepository userRepository;
    private PointMapper pointMapper;

    @Override
    @Transactional
    public PointDto assignPoints(PointAssigmentDto pointAssignDto) {
        User user = userRepository.findById(pointAssignDto.userId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario con ID: " + pointAssignDto.userId() + " no encontrado."));
        
        User assignedBy = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new IllegalStateException("Usuario autenticado no encontrado."));

        Point point = new Point();
        point.setUser(user);
        point.setPoints(pointAssignDto.points());
        point.setAssignedBy(assignedBy);
        point.setAssignmentDate(LocalDateTime.now());
        point.setDescription(String.format("Usuario %s (STAFF) asignó %d puntos a %s", assignedBy.getName(), pointAssignDto.points(), user.getName()));

        pointRepository.save(point);

        // Actualizar el saldo de puntos del usuario
        user.setPointsBalance(user.getPointsBalance() + pointAssignDto.points());
        userRepository.save(user);

        return pointMapper.pointToPointDto(point);
    }

    @Override
    public List<PointDto> getPointsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario con ID: " + userId + " no encontrado."));
        return pointRepository.findByUser(user).stream()
                .map(pointMapper::pointToPointDto)
                .toList();
    }

    @Override
    public List<PointDto> getAllPoints() {
        return pointRepository.findAll().stream()
                .map(pointMapper::pointToPointDto)
                .toList();
    }
}
