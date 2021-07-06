package com.example.blogapi.repository;

import com.example.blogapi.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {

   // List<Follow> findByCurrentUserIdAndAndFollowerId(Long currentUserId, Long followeeId);
    List<Follow> findByCurrentUserIdAndFolloweeId(Long currentUserId, Long followeeId);
    List<Follow> findAllByCurrentUserId(Long currentUserId);
    //List<Follow> findAllByFollowerId(Long  currentUserId);
    List<Follow> findAllByFolloweeId(Long currentUserId);


}
