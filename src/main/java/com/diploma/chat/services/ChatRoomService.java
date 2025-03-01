package com.diploma.chat.services;

import java.util.Optional;

public interface ChatRoomService {
    Optional<String> getChatRoomId(String senderId, String receiverId, boolean createNewRoomIfNotExists);
}
