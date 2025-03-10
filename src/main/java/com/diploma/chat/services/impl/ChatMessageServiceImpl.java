package com.diploma.chat.services.impl;

import com.diploma.chat.models.ChatMessage;
import com.diploma.chat.models.MessageStatus;
import com.diploma.chat.repositories.ChatMessageRepository;
import com.diploma.chat.services.ChatMessageService;
import com.diploma.chat.services.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatMessageServiceImpl implements ChatMessageService {
    private ChatMessageRepository repository;
    private ChatRoomService chatRoomService;

    @Transactional
    public ChatMessage save(ChatMessage chatMessage) {
        chatMessage.setStatus(MessageStatus.RECEIVED);
        repository.save(chatMessage);
        return chatMessage;
    }

    @Override
    public long countNewMessages(String senderId, String recipientId) {
        return repository.countBySenderIdAndReceiverIdAndStatus(
                senderId, recipientId, MessageStatus.RECEIVED);
    }

    @Transactional
    public List<ChatMessage> findChatMessages(String senderId, String recipientId) {
        var chatId = chatRoomService.getChatId(senderId, recipientId, false);

        var messages =
                chatId.map(cId -> repository.findByChatId(cId)).orElse(new ArrayList<>());

        if(!messages.isEmpty()) {
            updateStatuses(senderId, recipientId, MessageStatus.DELIVERED);
        }

        return messages;
    }

    @Override
    @Transactional
    public ChatMessage findById(Long id) {
        return repository
                .findById(id)
                .map(chatMessage -> {
                    chatMessage.setStatus(MessageStatus.DELIVERED);
                    return repository.save(chatMessage);
                })
                .orElseThrow(RuntimeException::new);
    }

    private void updateStatuses(String senderId, String receiverId, MessageStatus status) {
        List<ChatMessage> messages = repository.findBySenderIdAndReceiverId(senderId, receiverId);

        for (ChatMessage message : messages) {
            message.setStatus(status);
        }

        repository.saveAll(messages);
    }
}
