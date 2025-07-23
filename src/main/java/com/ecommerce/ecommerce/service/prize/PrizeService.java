package com.ecommerce.ecommerce.service.prize;

import java.util.List;

import com.ecommerce.ecommerce.dto.prize.PrizeActiveDto;
import com.ecommerce.ecommerce.dto.prize.PrizeCreateDto;
import com.ecommerce.ecommerce.dto.prize.PrizeDto;

public interface PrizeService {
    List<PrizeDto> getAllPrizes();
    PrizeDto createPrize(PrizeCreateDto prizeCreateDto);
    PrizeDto getPrizeById(Long id);
    PrizeDto updatePrize(Long id, PrizeCreateDto prizeCreateDto); // Para actualizar los demás atributos
    PrizeDto updatePrizeActive(Long id, PrizeActiveDto prizeActiveDto); // Nuevo método para actualizar active
}
