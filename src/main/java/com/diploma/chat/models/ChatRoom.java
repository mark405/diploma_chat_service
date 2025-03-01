package com.diploma.chat.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder()
@Entity(name = "chat_rooms")
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String chatId;

    private String senderId;

    private String receiverId;
}
