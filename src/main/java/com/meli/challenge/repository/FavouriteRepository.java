package com.meli.challenge.repository;

import com.meli.challenge.domain.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Repository
public interface FavouriteRepository extends JpaRepository<Favourite, String> {

    CompletableFuture <Favourite> findOneById(String id);

    List<Favourite> findTop5ByOrderByQuantityDesc();
}
