package com.ecommerce.ecommerce.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}