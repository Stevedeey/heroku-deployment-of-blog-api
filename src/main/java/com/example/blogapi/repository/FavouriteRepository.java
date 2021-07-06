package com.example.blogapi.repository;

import com.example.blogapi.model.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface FavouriteRepository extends JpaRepository<Favourite, Long> {
    List<Favourite> findFavouriteByCurrentUserId(Long id);
    List<Favourite> findFavouriteByCurrentUserIdAndPostId(Long userId, Long postId);

}
