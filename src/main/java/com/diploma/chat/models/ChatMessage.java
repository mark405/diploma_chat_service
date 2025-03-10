package com.diploma.chat.models;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder()
@Entity(name = "chat_messages")
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String chatId;

    private String senderId;

    private String senderName;

    private String receiverId;

    private String receiverName;

    private String content;

    private Date timestamp;

    private MessageStatus status;
}
