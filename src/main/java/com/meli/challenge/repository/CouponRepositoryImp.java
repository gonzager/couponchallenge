package com.meli.challenge.repository;

import com.meli.challenge.domain.ItemPrice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;


import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@Repository
@PropertySource("classpath:mercadolibre.properties")
@Slf4j
public class CouponRepositoryImp implements CouponRepository{

    private static final String ITEM_URL = "mercadolibre.url.items";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment env;

    /***
     * Consume asincronicamente los items de la URL de MELI
     * @param item_id
     * @return CompletableFuture<ItemPrice>
     */
    @Cacheable(value = "itemsmeli", key= "#item_id")
    @Override
    public CompletableFuture<ItemPrice> getItemPriceAsync(String item_id) {
        CompletableFuture<ItemPrice> future = CompletableFuture.supplyAsync(new Supplier<ItemPrice>() {
            @Override
            public ItemPrice get() {
                try {
                    return restTemplate.getForObject(env.getProperty(ITEM_URL), ItemPrice.class, item_id);
                } catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc)  {
                    log.error("Request Status Code: {} item_id: {}", httpClientOrServerExc.getStatusCode(), item_id);
                    return null;
                }
            }
        });
        return future;
    }


}
