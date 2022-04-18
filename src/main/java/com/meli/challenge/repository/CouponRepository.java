package com.meli.challenge.repository;


import com.meli.challenge.domain.ItemPrice;



import java.util.concurrent.CompletableFuture;

public interface CouponRepository {


    CompletableFuture<ItemPrice> getItemPriceAsync(String item_id);
}
