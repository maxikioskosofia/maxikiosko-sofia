package com.ecommerce.ecommerce.service.point;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.domain.Point;
import com.ecommerce.ecommerce.domain.User;
import com.ecommerce.ecommerce.dto.point.PointAssigmentDto;
import com.ecommerce.ecommerce.dto.point.PointDto;
import com.ecommerce.ecommerce.dto.point.PointUpdateDto;
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
        User user = userRepository.findByName(pointAssignDto.name())
                .orElseThrow(() -> new IllegalArgumentException("Usuario con nombre: " + pointAssignDto.name() + " no encontrado."));
        
        User assignedBy = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new IllegalStateException("Usuario autenticado no encontrado."));

        Point point = new Point();
        point.setUser(user);
        point.setPoints(pointAssignDto.points());
        point.setAssignedBy(assignedBy);
        point.setAssignmentDate(LocalDateTime.now());
        point.setDescription(String.format("Usuario %s (STAFF) asignó %d puntos a %s", 
                assignedBy.getName(), pointAssignDto.points(), user.getName()));

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

    @Override
    @Transactional
    public PointDto updatePoints(Long pointId, PointUpdateDto pointUpdateDto) {
        Point point = pointRepository.findById(pointId)
                .orElseThrow(() -> new IllegalArgumentException("Registro de puntos con ID: " + pointId + " no encontrado."));
        
        User user = point.getUser();
        User updatedBy = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new IllegalStateException("Usuario autenticado no encontrado."));

        // Calcular la diferencia de puntos para ajustar el saldo del usuario
        int oldPoints = point.getPoints();
        int newPoints = pointUpdateDto.points();
        int pointsDifference = newPoints - oldPoints;

        // Actualizar el registro de puntos
        point.setPoints(newPoints);
        point.setAssignmentDate(LocalDateTime.now());
        point.setDescription(String.format("Usuario %s (STAFF) actualizó los puntos a %d para %s", 
                updatedBy.getName(), newPoints, user.getName()));

        pointRepository.save(point);

        // Actualizar el saldo de puntos del usuario
        user.setPointsBalance(user.getPointsBalance() + pointsDifference);
        userRepository.save(user);

        return pointMapper.pointToPointDto(point);
    }

    @Override
    @Transactional
    public void deletePoints(Long pointId) {
        Point point = pointRepository.findById(pointId)
                .orElseThrow(() -> new IllegalArgumentException("Registro de puntos con ID: " + pointId + " no encontrado."));
        
        User user = point.getUser();
        
        // Restar los puntos del saldo del usuario
        user.setPointsBalance(user.getPointsBalance() - point.getPoints());
        userRepository.save(user);

        // Eliminar el registro de puntos
        pointRepository.delete(point);
    }
}
