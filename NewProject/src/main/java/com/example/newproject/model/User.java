package com.example.newproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String fullName;
    private String link;
    private String birthDate;
    private String email;
    private String password;
    private String mobile;
    private String image;
    private String bio;
    private boolean req_user;
    private boolean private_profile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Threads> threads;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Like> likes;

    @Embedded
    private Varification verification;

    @JsonIgnore
    @ManyToMany
    private List<User> followers;

    @JsonIgnore
    @ManyToMany
    private List<User> followings;

}

