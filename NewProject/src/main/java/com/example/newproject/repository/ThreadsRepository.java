package com.example.newproject.repository;

import com.example.newproject.model.Threads;
import com.example.newproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ThreadsRepository extends JpaRepository<Threads, Long> {

    List<Threads> findAllByIsThreadTrueOrderByCreatedAtDesc();

    List<Threads> findByUser_IdAndIsThreadTrueOrderByCreatedAtDesc(Long userId);
    List<Threads> findByRepostUserContainsOrderByCreatedAtDesc(User user);

    List<Threads> findByLikesContainingOrderByCreatedAtDesc(User user);

    @Query(value = "Select t From Threads t JOIN t.likes l where l.user.id = :userId")
    List<Threads> findByLikesUser_id(Long userId);
}
