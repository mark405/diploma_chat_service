package com.diploma.chat.repositories;

import com.diploma.chat.models.ChatMessage;
import com.diploma.chat.models.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatId(String chatId);

    List<ChatMessage> findBySenderIdAndReceiverId(String senderId, String receiverId);

    long countBySenderIdAndReceiverIdAndStatus(String senderId, String recipientId, MessageStatus status);
}
