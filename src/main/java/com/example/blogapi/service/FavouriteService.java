package com.example.blogapi.service;

import com.example.blogapi.dto.FavouriteMapper;
import com.example.blogapi.model.Person;

import java.util.List;

public interface FavouriteService {
    List<FavouriteMapper> getFavouritesByUser(Person person);
    boolean saveFavourite(Person person, Long postId);
}
