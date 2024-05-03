package com.hyperhire.whtsapp.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hyperhire.whtsapp.model.ChatRoom;
import com.hyperhire.whtsapp.model.Message;
import com.hyperhire.whtsapp.model.User;
import com.hyperhire.whtsapp.repository.ChatroomRepo;
import com.hyperhire.whtsapp.repository.MessageRepo;
import com.hyperhire.whtsapp.repository.UserRepo;
@Service
public class MessageService {

	@Autowired
	private MessageRepo messageRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private ChatroomRepo chatroomRepo;

	public Message sendMessage(Message message) {
		return messageRepo.save(message);
	}

	public Page<Message> getAllMessagesInChatroom(Long chatroomId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return messageRepo.findByChatroomId(chatroomId, pageable);
    }

	public Message sendMessageWithAttachment(MultipartFile file, Long userId, Long chatroomId, String text)
			throws IOException {
		
		if (file.getSize() > 10 * 1024 * 1024) {
			throw new IllegalArgumentException("Attachment size exceeds 10MB limit.");
		}

		String filePath = "root/picture/" + file.getOriginalFilename();
		file.transferTo(new File(filePath));

		User sender = userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found."));
		ChatRoom chatroom = chatroomRepo.findById(chatroomId)
				.orElseThrow(() -> new IllegalArgumentException("Chatroom not found."));

		Message message = new Message();
		message.setSender(sender);
		message.setChatroom(chatroom);
		message.setText(text);
		message.setAttachmentPath(filePath);
		return messageRepo.save(message);
	}

	public Message respondWithEmoji(Long messageId, String emoji) {
		Message message = messageRepo.findById(messageId).orElse(null);
		if (message != null) {
			message.setEmoji(emoji);
			return messageRepo.save(message);
		} else {
			throw new IllegalArgumentException("Message not found with ID: " + messageId);
		}
	}

}
