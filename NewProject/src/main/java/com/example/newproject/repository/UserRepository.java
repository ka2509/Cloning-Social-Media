package com.example.newproject.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.newproject.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByEmail(String email);
    @Query(value = "SELECT DISTINCT u FROM User u WHERE u.fullName LIKE %:query% OR u.email LIKE %:query%", nativeQuery = true)
    public List<User> searchUser(@Param("query") String query);

}
