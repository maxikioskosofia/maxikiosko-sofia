package com.ecommerce.ecommerce.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.dto.point.PointAssigmentDto;
import com.ecommerce.ecommerce.dto.point.PointDto;
import com.ecommerce.ecommerce.dto.point.PointUpdateDto;
import com.ecommerce.ecommerce.service.point.PointService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/points")
@AllArgsConstructor
public class PointController {
    private PointService pointService;

    @PostMapping("/assign")
    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    public ResponseEntity<PointDto> assignPoints(@RequestBody @Valid PointAssigmentDto pointAssignDto) {
        return ResponseEntity.ok(pointService.assignPoints(pointAssignDto));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF') or @securityService.isUser(#userId)")
    public ResponseEntity<List<PointDto>> getPointsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(pointService.getPointsByUser(userId));
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PointDto>> getAllPoints(){
        return ResponseEntity.ok(pointService.getAllPoints());
    }

    @PutMapping("/{pointId}")
    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    public ResponseEntity<PointDto> updatePoints(@PathVariable Long pointId, @RequestBody @Valid PointUpdateDto pointUpdateDto) {
        return ResponseEntity.ok(pointService.updatePoints(pointId, pointUpdateDto));
    }

    @DeleteMapping("/{pointId}")
    @PreAuthorize("hasRole('STAFF') or hasRole('ADMIN')")
    public ResponseEntity<Void> deletePoints(@PathVariable Long pointId) {
        pointService.deletePoints(pointId);
        return ResponseEntity.noContent().build();
    }
}
