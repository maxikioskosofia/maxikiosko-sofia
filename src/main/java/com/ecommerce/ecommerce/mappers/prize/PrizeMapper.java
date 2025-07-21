package com.ecommerce.ecommerce.mappers.prize;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ecommerce.ecommerce.domain.Prize;
import com.ecommerce.ecommerce.dto.prize.PrizeCreateDto;
import com.ecommerce.ecommerce.dto.prize.PrizeDto;

@Mapper(componentModel = "spring")
public interface PrizeMapper {
    @Mapping(target = "id", ignore = true)
    Prize prizeCreateDtoToPrize(PrizeCreateDto prizeCreateDto);

    PrizeDto prizeToPrizeDto(Prize prize);
    
}