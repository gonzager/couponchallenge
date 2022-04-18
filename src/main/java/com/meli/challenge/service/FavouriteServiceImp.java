package com.meli.challenge.service;
import com.meli.challenge.domain.Favourite;
import com.meli.challenge.repository.FavouriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class FavouriteServiceImp implements FavouriteService {
    @Autowired
    FavouriteRepository favoriteRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<Favourite> findById(String id) {
        return favoriteRepository.findById(id);
    }

    @Transactional
    @Async
    @Override
    public void saveAll(List<Favourite> favourites) {
         favoriteRepository.saveAll(favourites);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Favourite> findTop5ByOrderByQuantityDesc() {
        return favoriteRepository.findTop5ByOrderByQuantityDesc();
    }

    private List<Favourite> getFavourites(List<String> ids){

        List<CompletableFuture<Favourite>> futures = ids.stream()
                .map(id -> favoriteRepository.findOneById(id) )
                .collect(Collectors.toList());

        return  futures.stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());

    }
}
