package com.ecommerce.ecommerce.service.prize;

import java.util.List;

import com.ecommerce.ecommerce.dto.prize.PrizeCreateDto;
import com.ecommerce.ecommerce.dto.prize.PrizeDto;

public interface PrizeService {
    List<PrizeDto> getAllPrizes();
    PrizeDto createPrize(PrizeCreateDto prizeCreateDto);
    PrizeDto getPrizeById(Long id);
}
