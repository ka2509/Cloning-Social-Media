package com.example.newproject.repository;

import com.example.newproject.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

public interface LikeRepository extends JpaRepository<Like, Long> {
    @Query("SELECT l FROM Like l WHERE l.user.id = :userId AND l.thread.id = :threadId")
    public Like isLikeExist(@Param("userId") Long userId, @Param("threadId") Long threadId);

    @Query("SELECT l FROM Like l WHERE l.thread.id = :threadId")
    public List<Like> findByThreadId(@Param("threadId") Long threadId);
}
