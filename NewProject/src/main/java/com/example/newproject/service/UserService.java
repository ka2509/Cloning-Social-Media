package com.example.newproject.service;

import com.example.newproject.config.JwtProvider;
import com.example.newproject.exception.UserException;
import com.example.newproject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.newproject.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProvider jwtProvider;

    public User findUserById(Long userId) throws UserException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserException("this user is not exist with this id : " + userId));
    }

    public User findUserProfileByJwt(String jwt) throws UserException {
        String email = jwtProvider.getEmailFromToken(jwt);
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserException("User not found with email : " + email);
        }
        return user;
    }

    public User updateUser(Long userId, User req) throws UserException {
        User user = findUserById(userId);
        if(req.getFullName() != null) {
            user.setFullName(req.getFullName());
        }
        if(req.getImage() != null) {
            user.setImage(req.getImage());
        }
        if (req.getBio() != null) {
            user.setBio(req.getBio());
        }
        if (req.getLink() != null) {
            user.setLink(req.getLink());
        }
        if (req.getBirthDate() != null) {
            user.setBirthDate(req.getBirthDate());
        }
        if (req.isPrivate_profile() != user.isPrivate_profile()) {
            user.setPrivate_profile(req.isPrivate_profile());
        }
        return userRepository.save(user);
    }

    public User followUser(Long userId, User user) throws UserException {
        User followToUser = findUserById(userId);

        if(user.getFollowings().contains(followToUser) && followToUser.getFollowers().contains(user)) {
            followToUser.getFollowers().remove(user);
            user.getFollowings().remove(followToUser);
        }
        else {
            followToUser.getFollowers().add(user);
            user.getFollowings().add(followToUser);
        }
        userRepository.save(followToUser);
        userRepository.save(user);
        return followToUser;
    }

    public List<User> searchUser(String query) {
        return userRepository.searchUser(query);
    }

    //custom UserDetailsService of Spring Security
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new UsernameNotFoundException("Email not found!");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }
}
