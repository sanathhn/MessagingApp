package com.hyperhire.whtsapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyperhire.whtsapp.model.ChatRoom;
import com.hyperhire.whtsapp.repository.ChatroomRepo;

@Service
public class ChatroomService {
     
	@Autowired
    private ChatroomRepo chatroomRepo;

    public ChatRoom createChatroom(ChatRoom chatroom) {
        return chatroomRepo.save(chatroom);
    }

    public List<ChatRoom> getAllChatrooms() {
        return chatroomRepo.findAll();
    }

    public ChatRoom getChatroomById(Long chatroomId) {
        return chatroomRepo.findById(chatroomId).orElse(null);
    }
}
