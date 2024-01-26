package com.example.newproject.service;

import com.example.newproject.exception.ThreadsException;
import com.example.newproject.exception.UserException;
import com.example.newproject.model.Like;
import com.example.newproject.model.Threads;
import com.example.newproject.model.User;
import com.example.newproject.repository.LikeRepository;
import com.example.newproject.repository.ThreadsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private ThreadsService threadService;
    @Autowired
    private ThreadsRepository threadsRepository;

    public Like likeThread(Long threadId, User user) throws UserException, ThreadsException {
        Like isLikeExist = likeRepository.isLikeExist(user.getId(), threadId);
        if(isLikeExist != null) {
            likeRepository.deleteById(isLikeExist.getId());
            return isLikeExist;
        }
        Threads thread = threadService.findById(threadId);
        Like like = new Like();
        like.setThread(thread);
        like.setUser(user);
        thread.getLikes().add(like);
        threadsRepository.save(thread);
        return likeRepository.save(like);
    }

    public List<Like> getAllLikes(Long threadId) throws ThreadsException {
        Threads thread = threadService.findById(threadId);
        return likeRepository.findByThreadId(thread.getId());
    }
}
