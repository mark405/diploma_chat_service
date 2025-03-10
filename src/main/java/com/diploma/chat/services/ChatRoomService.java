package com.diploma.chat.services;

import java.util.Optional;

public interface ChatRoomService {
    Optional<String> getChatId(String senderId, String receiverId, boolean createNewRoomIfNotExists);
}
