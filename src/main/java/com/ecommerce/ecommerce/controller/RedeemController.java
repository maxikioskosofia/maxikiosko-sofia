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

import com.ecommerce.ecommerce.dto.redeem.RedeemCreateDto;
import com.ecommerce.ecommerce.dto.redeem.RedeemDto;
import com.ecommerce.ecommerce.service.redeem.RedeemService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/redeems")
@AllArgsConstructor
public class RedeemController {
    private RedeemService redeemService;

    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('STAFF') or hasRole('ADMIN')")
    public ResponseEntity<RedeemDto> redeemPrize(@RequestBody @Valid RedeemCreateDto redeemCreateDto) {
        return ResponseEntity.ok(redeemService.redeemPrize(redeemCreateDto));
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF') or @securityService.isUser(#userId)")
    public ResponseEntity<List<RedeemDto>> getRedeemsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(redeemService.getRedeemsByUser(userId));
    }

    @GetMapping
    public ResponseEntity<List<RedeemDto>> getAllRedeem() {
        return ResponseEntity.ok(redeemService.getAllRedeem());
    }
}