package com.example.blogapi.repository;

import com.example.blogapi.model.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
    List<Favourite> findFavouriteByCurrentUserId(Long id);
}
