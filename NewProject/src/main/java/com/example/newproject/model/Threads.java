package com.example.newproject.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data

public class Threads {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private User user;

    private String content;
    private String image;
    private String video;

    @OneToMany(mappedBy = "thread", cascade = CascadeType.ALL)
    private List<Like> likes;

    @OneToMany(mappedBy = "replyFor", cascade = CascadeType.ALL)
    private List<Threads> replies;

    @ManyToMany
    private List<User> repost;

    @ManyToOne
    private Threads replyFor;

    private boolean isReply;
    private boolean isThread;

}
