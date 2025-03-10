package com.diploma.chat.services;

import com.diploma.chat.models.ChatMessage;

import java.util.List;

public interface ChatMessageService {
    ChatMessage save(ChatMessage chatMessage);
    List<ChatMessage> findChatMessages(String senderId, String receiverId);
    long countNewMessages(String senderId, String recipientId);
    ChatMessage findById(Long id);
}
