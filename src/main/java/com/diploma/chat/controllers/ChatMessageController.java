package com.diploma.chat.controllers;

import com.diploma.chat.models.ChatMessage;
import com.diploma.chat.models.ChatNotificationModel;
import com.diploma.chat.services.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatMessageController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat")
    public void processMessage(ChatMessage chatMessage) {
        ChatMessage savedMessage = chatMessageService.save(chatMessage);
        simpMessagingTemplate.convertAndSendToUser(
                savedMessage.getReceiverId(),
                "/queue/messages",
                ChatNotificationModel.builder()
                        .id(chatMessage.getId())
                        .senderId(chatMessage.getSenderId())
                        .receiverId(chatMessage.getReceiverId())
                        .content(chatMessage.getContent())
                        .build()
        );
    }

    @GetMapping("/messages/{senderId}/{receiverId}")
    public ResponseEntity<List<ChatMessage>> findChatMessages(
            @PathVariable("senderId") String senderId,
            @PathVariable("receiverId") String receiverId
    ) {
        return ResponseEntity.ok(chatMessageService.findChatMessages(senderId, receiverId));
    }
}
