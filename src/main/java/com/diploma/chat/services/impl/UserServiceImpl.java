package com.diploma.chat.services.impl;

import com.diploma.chat.enums.UserStatus;
import com.diploma.chat.models.User;
import com.diploma.chat.repositories.UsersRepository;
import com.diploma.chat.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UsersRepository usersRepository;

    @Override
    @Transactional
    public User save(User user) {
        user.setStatus(UserStatus.ONLINE);
        return usersRepository.save(user);
    }

    @Override
    @Transactional
    public User disconnect(Long id) {
        User user = usersRepository.findById(id).orElseThrow();
        user.setStatus(UserStatus.OFFLINE);
        return usersRepository.save(user);
    }

    @Override
    public List<User> findAllConnectedUsers() {
        return usersRepository.findAllByStatus(UserStatus.ONLINE);
    }
}
