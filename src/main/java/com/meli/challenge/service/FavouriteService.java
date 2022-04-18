package com.meli.challenge.service;

import com.meli.challenge.domain.Favourite;

import java.util.List;
import java.util.Optional;

public interface FavouriteService {
    Optional<Favourite> findById(String id);

    void saveAll(List<Favourite> faboriteados);

    List<Favourite> findTop5ByOrderByQuantityDesc();
}
