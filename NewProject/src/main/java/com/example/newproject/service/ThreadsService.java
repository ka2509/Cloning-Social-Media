package com.example.newproject.service;

import com.example.newproject.exception.ThreadsException;
import com.example.newproject.exception.UserException;
import com.example.newproject.model.Threads;
import com.example.newproject.model.User;
import com.example.newproject.repository.ThreadsRepository;
import com.example.newproject.request.ThreadsReplyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ThreadsService {


    @Autowired
    public ThreadsRepository threadsRepository;

    public Threads createThread(Threads thread, User user) throws UserException{
        Threads newThread = new Threads();
        newThread.setContent(thread.getContent());
        newThread.setCreatedAt(LocalDateTime.now());
        newThread.setImage(thread.getImage());
        newThread.setUser(user);
        newThread.setReply(false);
        newThread.setThread(true);
        newThread.setVideo(thread.getVideo());

        return threadsRepository.save(newThread);
    }

    public List<Threads> findAllThreads() {
        return threadsRepository.findAllByIsThreadTrueOrderByCreatedAtDesc();
    }

    public Threads repost(Long threadId, User user) throws UserException, ThreadsException {
        Threads thread = findById(threadId);
        if(thread.getRepostUser().contains(user)) {
            thread.getRepostUser().remove(user);
        }
        else {
            thread.getRepostUser().add(user);
        }
        return threadsRepository.save(thread);
    }

    public Threads findById(Long threadId) throws ThreadsException {
        return threadsRepository.findById(threadId).orElseThrow(() -> new ThreadsException("Thread not found with this id : + " + threadId));
    }

    public void deleteThreadById(Long threadId, Long userId) throws UserException, ThreadsException {
        Threads thread = findById(threadId);
        if(!userId.equals(thread.getUser().getId())) {
            throw new UserException("you can't delete another user's twit");
        }
        threadsRepository.deleteById(threadId);
    }

//    public Threads deleteRepost(Long threadId, User user) throws UserException, ThreadsException {
//
//    }

    public Threads createReply(ThreadsReplyRequest thread, User user) throws ThreadsException {
        Threads replyFor = findById(thread.getThreadID());
        Threads newThread = new Threads();
        newThread.setContent(thread.getContent());
        newThread.setCreatedAt(LocalDateTime.now());
        newThread.setImage(thread.getImage());
        newThread.setUser(user);
        newThread.setReply(true);
        newThread.setThread(false);
        newThread.setVideo(thread.getVideo());
        newThread.setReplyFor(replyFor);
        threadsRepository.save(newThread);
        replyFor.getReplies().add(newThread);
        return threadsRepository.save(replyFor);
    }

    public List<Threads> getUserThreads(Long userId) {
        return threadsRepository.findByUser_IdAndIsThreadTrueOrderByCreatedAtDesc(userId);
    }
    public List<Threads> getUserRepost(User user) {
        return threadsRepository.findByRepostUserContainsOrderByCreatedAtDesc(user);
    }
    public List<Threads> findByLikesContainsUser(User user) {
        return  threadsRepository.findByLikesContainingOrderByCreatedAtDesc(user);
    }
}
