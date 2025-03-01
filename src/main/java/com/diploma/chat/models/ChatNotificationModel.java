package com.diploma.chat.models;

import lombok.*;

@Getter
@Setter
@Builder()
@AllArgsConstructor
@NoArgsConstructor
public class ChatNotificationModel {
    private Long id;

    private String senderId;

    private String receiverId;

    private String content;
}
