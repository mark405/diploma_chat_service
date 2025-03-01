package com.diploma.chat.services.impl;

import com.diploma.chat.models.ChatRoom;
import com.diploma.chat.repositories.ChatRoomRepository;
import com.diploma.chat.services.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    @Override
    @Transactional
    public Optional<String> getChatRoomId(String senderId, String receiverId, boolean createNewRoomIfNotExists) {
        return chatRoomRepository.findBySenderIdAndReceiverId(senderId, receiverId)
                .map(ChatRoom::getChatId)
                .or(() -> {
                    if (createNewRoomIfNotExists) {
                        var chatId = createChatId(senderId, receiverId);
                        return Optional.of(chatId);
                    }

                    return Optional.empty();
                });
    }

    public String createChatId(String senderId, String receiverId) {
        String chatId = senderId + "_" + receiverId;

        ChatRoom sendReceiver = ChatRoom.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .chatId(chatId)
                .build();

        ChatRoom receiverSender = ChatRoom.builder()
                .senderId(receiverId)
                .receiverId(senderId)
                .chatId(chatId)
                .build();

        chatRoomRepository.save(sendReceiver);
        chatRoomRepository.save(receiverSender);

        return chatId;
    }
}
