package com.ecommerce.ecommerce.service.redeem;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.domain.Prize;
import com.ecommerce.ecommerce.domain.Redeem;
import com.ecommerce.ecommerce.domain.User;
import com.ecommerce.ecommerce.domain.enums.StatusEnumRedeem;
import com.ecommerce.ecommerce.dto.redeem.RedeemCreateDto;
import com.ecommerce.ecommerce.dto.redeem.RedeemDto;
import com.ecommerce.ecommerce.mappers.redeem.RedeemMapper;
import com.ecommerce.ecommerce.repository.PrizeRepository;
import com.ecommerce.ecommerce.repository.RedeemRepository;
import com.ecommerce.ecommerce.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RedeemServiceImpl implements RedeemService{
    private RedeemRepository redeemRepository;
    private UserRepository userRepository;
    private PrizeRepository prizeRepository;
    private RedeemMapper redeemMapper;

    @Override
    @Transactional
    public RedeemDto redeemPrize(RedeemCreateDto redeemCreateDto) {
        User user = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new IllegalStateException("Usuario autenticado no encontrado."));

        Prize prize = prizeRepository.findById(redeemCreateDto.prizeId())
                .orElseThrow(() -> new IllegalArgumentException("Premio con ID: " + redeemCreateDto.prizeId() + " no encontrado."));

        if (!prize.getActive()) {
            throw new IllegalStateException("El premio no está activo.");
        }

        if (prize.getStock() <= 0) {
            throw new IllegalStateException("El premio no tiene stock disponible.");
        }

        if (user.getPointsBalance() < prize.getPointsCost()) {
            throw new IllegalStateException("El usuario no tiene suficientes puntos para canjear este premio.");
        }

        Redeem redeem = new Redeem();
        redeem.setUser(user);
        redeem.setPrize(prize);
        redeem.setPointsUsed(prize.getPointsCost());
        redeem.setRedeemDate(LocalDateTime.now());
        redeem.setStatus(StatusEnumRedeem.PENDIENTE);
        redeem.setDescription(String.format("Usuario %s canjeó %s por %d puntos", 
            user.getName(), prize.getName(), prize.getPointsCost()));

        // Actualizar saldo de puntos del usuario y stock del premio
        user.setPointsBalance(user.getPointsBalance() - prize.getPointsCost());
        prize.setStock(prize.getStock() - 1);
        
        redeemRepository.save(redeem);
        userRepository.save(user);
        prizeRepository.save(prize);

        return redeemMapper.redeemToRedeemDto(redeem);
    }

    @Override
    public List<RedeemDto> getRedeemsByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario con ID: " + userId + " no encontrado."));
        return redeemRepository.findByUser(user).stream()
                .map(redeemMapper::redeemToRedeemDto)
                .toList();
    }
}
