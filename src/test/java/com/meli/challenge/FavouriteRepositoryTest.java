package com.meli.challenge;

import com.meli.challenge.domain.Favourite;
import com.meli.challenge.repository.FavouriteRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@Sql({"/import_favourite_test.sql"})
@ActiveProfiles("test")
public class FavouriteRepositoryTest {

    @Autowired
    FavouriteRepository favouriteRepository;

    @Test
    void firstValueOK() {
        favouriteRepository.saveAll(Arrays.asList(new Favourite("FAV1", 500), new Favourite("FAV2", 123456789)));
        List<Favourite> favourites = favouriteRepository.findTop5ByOrderByQuantityDesc();
        assertEquals(123456789,favourites.stream().findFirst().get().getQuantity() );
    }

    @Test
    void findTop5ByOrderByQuantityDesc() {
        List<Favourite> favourites = favouriteRepository.findTop5ByOrderByQuantityDesc();
        assertEquals(5, favourites.size());
    }


}
