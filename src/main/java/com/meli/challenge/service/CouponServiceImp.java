package com.meli.challenge.service;

import com.meli.challenge.domain.ItemPrice;
import com.meli.challenge.domain.Coupon;
import com.meli.challenge.domain.Favourite;
import com.meli.challenge.process.Maximizer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CouponServiceImp implements CouponService {

    @Autowired
    private Maximizer maximizer;

    @Autowired ItemPriceService itemPriceService;

    @Autowired
    FavouriteService favoriteService;

    @Override
    public Coupon  getMaximizedCoupon(Coupon coupon) {

        List<ItemPrice> itemsPriceDTO = maximizer.process(
                itemPriceService.getItemPriceDTO(coupon),
                coupon.getAmount()
        );

        List<Favourite> favourites = itemsPriceDTO.parallelStream().map(
                id-> {
                    Favourite f = favoriteService.findById(id.getId()).orElse(new Favourite(id.getId(), 0));
                    f.setQuantity(f.getQuantity()+1);
                    return f;
                    }
        ).collect(Collectors.toList());

        favoriteService.saveAll(favourites);

        return itemsPriceDTO.stream()
                .map( itemPriceDTO -> new Coupon(Arrays.asList(itemPriceDTO.getId()), itemPriceDTO.getPrice()))
                .reduce( new Coupon(new ArrayList<String>(), new BigDecimal(0)),(a,b) ->
                {
                    a.getItems_ids().addAll(b.getItems_ids());
                    a.setAmount(a.getAmount().add(b.getAmount()));
                    return a;
                });
    }
}
