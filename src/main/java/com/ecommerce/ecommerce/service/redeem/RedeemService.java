package com.ecommerce.ecommerce.service.redeem;

import java.util.List;

import com.ecommerce.ecommerce.dto.redeem.RedeemCreateDto;
import com.ecommerce.ecommerce.dto.redeem.RedeemDto;

public interface RedeemService {
    List<RedeemDto> getRedeemsByUser(Long userId);
    RedeemDto redeemPrize(RedeemCreateDto redeemCreateDto);
    List<RedeemDto> getAllRedeem();
}
