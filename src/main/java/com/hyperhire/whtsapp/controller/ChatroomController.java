package com.hyperhire.whtsapp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyperhire.whtsapp.model.ChatRoom;

import com.hyperhire.whtsapp.service.ChatroomService;

@RestController
@RequestMapping("/whtsapp/chatroom")
public class ChatroomController {
	@Autowired
	
	ChatroomService chatroomService;
	
	@PostMapping("/create")
    public ResponseEntity<ChatRoom> createChatroom(@RequestBody ChatRoom chatroom) {
		ChatRoom newchatroom=chatroomService.createChatroom(chatroom);
		return new ResponseEntity<ChatRoom>(newchatroom, HttpStatus.CREATED);
		
    }
	
	
}
