package com.hyperhire.whtsapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyperhire.whtsapp.model.User;
import com.hyperhire.whtsapp.service.UserService;

@RestController
@RequestMapping("/whtsapp/profile/")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/getAll")
	public ResponseEntity<List<User>> getAllUserProfile() {
	    List<User> users = userService.getAllUsers();
	    if (users == null) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	    return new ResponseEntity<>(users, HttpStatus.OK);
	}
	
	
	@GetMapping("getbyid/{id}")
	public ResponseEntity<User> getUserProfileById(@PathVariable Long id) {
		    Optional<User> user = userService.getUserById(id);
		    if (!user.isPresent()) {
		        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		    }
		    User user1 = user.get();
		    return new ResponseEntity<>(user1, HttpStatus.OK);
		
	}

	@PostMapping("/create")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		User createdUser = userService.createUser(user);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<User> updateUserProfile(@PathVariable Long id, @RequestBody User updatedUser) {
	    Optional<User> userOptional = userService.getUserById(id);
	    if (!userOptional.isPresent()) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	    User user = userOptional.get();
	    user.setProfilePictureUrl(updatedUser.getProfilePictureUrl());
	    user.setStatus(updatedUser.getStatus());
	    User savedUser = userService.updateUserProfile(user);
	    return new ResponseEntity<>(savedUser, HttpStatus.OK);
	}
}