package com.hyperhire.whtsapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyperhire.whtsapp.exception.DuplicateException;
import com.hyperhire.whtsapp.model.User;
import com.hyperhire.whtsapp.repository.UserRepo;

@Service
public class UserService {
	
	@Autowired
    private  UserRepo userRepo;

    

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
    
    public Optional<User> getUserById(Long id) {
        return userRepo.findById(id);
    }
    
    public User createUser(User user) {
        Optional<User> existingUser = userRepo.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            throw new DuplicateException("Username already exists");
        }
        user.setId(null); 
        return userRepo.save(user);
    }

    public User updateUserProfile(User user) {
        return userRepo.save(user);
    }
}