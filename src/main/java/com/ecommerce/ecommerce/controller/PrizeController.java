package com.ecommerce.ecommerce.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.ecommerce.dto.prize.PrizeActiveDto;
import com.ecommerce.ecommerce.dto.prize.PrizeCreateDto;
import com.ecommerce.ecommerce.dto.prize.PrizeDto;
import com.ecommerce.ecommerce.service.prize.PrizeService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/prizes")
@AllArgsConstructor
public class PrizeController {
    private PrizeService prizeService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PrizeDto> createPrize(@RequestBody @Valid PrizeCreateDto prizeCreateDto) {
        return ResponseEntity.ok(prizeService.createPrize(prizeCreateDto));
    }

    @GetMapping
    public ResponseEntity<List<PrizeDto>> getAllPrizes() {
        return ResponseEntity.ok(prizeService.getAllPrizes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrizeDto> getPrizeById(@PathVariable Long id) {
        return ResponseEntity.ok(prizeService.getPrizeById(id));
    }

    @PatchMapping("/{id}/active")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PrizeDto> updatePrizeActive(@PathVariable Long id, @RequestBody @Valid PrizeActiveDto prizeActiveDto) {
        return ResponseEntity.ok(prizeService.updatePrizeActive(id, prizeActiveDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PrizeDto> updatePrize(@PathVariable Long id, @RequestBody @Valid PrizeCreateDto prizeCreateDto) {
        return ResponseEntity.ok(prizeService.updatePrize(id, prizeCreateDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePrize(@PathVariable Long id) {
        prizeService.deletePrize(id);
        return ResponseEntity.noContent().build();
    }
}