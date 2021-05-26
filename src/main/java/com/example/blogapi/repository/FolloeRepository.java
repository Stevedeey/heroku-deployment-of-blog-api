package com.example.blogapi.repository;

import com.example.blogapi.model.Follow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FolloeRepository extends JpaRepository<Follow, Long> {
}
