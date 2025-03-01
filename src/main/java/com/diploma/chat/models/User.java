package com.diploma.chat.models;

import com.diploma.chat.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder()
@Entity(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    private String username;

    private String fullName;

    private UserStatus status;
}
