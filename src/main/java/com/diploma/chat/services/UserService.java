package com.diploma.chat.services;

import com.diploma.chat.models.User;

import java.util.List;

public interface UserService {
    User save(User user);

    User disconnect(Long id);

    List<User> findAllConnectedUsers();
}
