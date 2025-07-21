package com.ecommerce.ecommerce.service.prize;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.ecommerce.domain.Prize;
import com.ecommerce.ecommerce.dto.prize.PrizeCreateDto;
import com.ecommerce.ecommerce.dto.prize.PrizeDto;
import com.ecommerce.ecommerce.mappers.prize.PrizeMapper;
import com.ecommerce.ecommerce.repository.PrizeRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PrizeServiceImpl implements PrizeService{
    private PrizeRepository prizeRepository;
    private PrizeMapper prizeMapper;

    @Override
    public PrizeDto createPrize(PrizeCreateDto prizeCreateDto) {
        Prize prize = prizeMapper.prizeCreateDtoToPrize(prizeCreateDto);
        prize.setActive(true);
        prizeRepository.save(prize);
        return prizeMapper.prizeToPrizeDto(prize);
    }

    @Override
    public List<PrizeDto> getAllPrizes() {
        return prizeRepository.findAll().stream()
                .map(prizeMapper::prizeToPrizeDto)
                .toList();
    }

    @Override
    public PrizeDto getPrizeById(Long id) {
        Prize prize = prizeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Premio con ID: " + id + " no encontrado."));
        return prizeMapper.prizeToPrizeDto(prize);
    }
}
