package com.meli.challenge.service;

import com.meli.challenge.domain.ItemPrice;
import com.meli.challenge.domain.Coupon;

import java.util.List;

public interface ItemPriceService {
    List<ItemPrice> getItemPriceDTO(Coupon coupon);
}
