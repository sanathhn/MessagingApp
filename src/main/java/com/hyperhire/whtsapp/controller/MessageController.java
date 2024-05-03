package com.hyperhire.whtsapp.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hyperhire.whtsapp.model.Message;
import com.hyperhire.whtsapp.service.MessageService;

@RestController
@RequestMapping("whtsapp/message")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/send")
    public Message sendMessage(@RequestBody Message message) {
        return messageService.sendMessage(message);
   
    }
    
    @PostMapping("/sendwithattachment")
    public Message sendMessageWithAttachment(@RequestParam("file") MultipartFile file,
                                             @RequestParam("userId") Long userId,
                                             @RequestParam("chatroomId") Long chatroomId,
                                             @RequestParam("text") String text) throws IOException {
        return messageService.sendMessageWithAttachment(file, userId, chatroomId, text);
    }
    
    //Pagaination implemented here
    @GetMapping("/chatroom/{chatroomId}")
    public ResponseEntity<List<Message>> getAllMessagesInChatroom(
            @PathVariable Long chatroomId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        
        Page<Message> messagesPage = messageService.getAllMessagesInChatroom(chatroomId, page, size);
        List<Message> messages = messagesPage.getContent();
        
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("X-Total-Count", String.valueOf(messagesPage.getTotalElements()));
        
        return new ResponseEntity<>(messages, responseHeaders, HttpStatus.OK);
    }
    
    @PostMapping("/respondwithemoji")
    public Message respondWithEmoji(@RequestParam("messageId") Long messageId,
                                    @RequestParam("emoji") String emoji) {
        return messageService.respondWithEmoji(messageId, emoji);
    }
}