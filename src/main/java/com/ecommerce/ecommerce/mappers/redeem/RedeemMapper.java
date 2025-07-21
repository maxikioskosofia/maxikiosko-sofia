package com.ecommerce.ecommerce.mappers.redeem;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ecommerce.ecommerce.domain.Redeem;
import com.ecommerce.ecommerce.dto.redeem.RedeemDto;

@Mapper(componentModel = "spring")
public interface RedeemMapper {
    @Mapping(target = "userId", source = "user.id_user")
    @Mapping(target = "userName", source = "user.name")
    @Mapping(target = "prizeId", source = "prize.id")
    @Mapping(target = "prizeName", source = "prize.name")
    RedeemDto redeemToRedeemDto(Redeem redeem);
}
