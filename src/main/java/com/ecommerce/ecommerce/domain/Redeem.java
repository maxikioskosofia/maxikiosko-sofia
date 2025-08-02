package com.ecommerce.ecommerce.domain;

import java.time.LocalDateTime;

import com.ecommerce.ecommerce.domain.enums.StatusEnumRedeem;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;


@Entity
@Data
public class Redeem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "prize_id", nullable = false)
    private Prize prize;

    @Column(nullable = false)
    private Integer pointsUsed;

    @Column(nullable = false)
    private LocalDateTime redeemDate;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusEnumRedeem status;

    @Column
    private String description;
}
