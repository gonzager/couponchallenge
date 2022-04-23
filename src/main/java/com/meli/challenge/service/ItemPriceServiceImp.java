package com.meli.challenge.service;

import com.meli.challenge.domain.ItemPrice;
import com.meli.challenge.domain.Coupon;
import com.meli.challenge.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service

public class ItemPriceServiceImp implements ItemPriceService{
    @Autowired
    private CouponRepository couponRepository;


    public List<ItemPrice> getItemPriceDTO(Coupon coupon){
        List<CompletableFuture<ItemPrice>> futures = coupon.getItems_ids().stream()
                .map(id -> couponRepository.getItemPriceAsync(id))
                .collect(Collectors.toList());

        return  futures.stream()
                .map(CompletableFuture::join)
                .filter(e->e != null)
                .filter(e->e.getStatus().equals("active"))
                .collect(Collectors.toList());

    }


}
