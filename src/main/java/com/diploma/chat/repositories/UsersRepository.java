package com.diploma.chat.repositories;

import com.diploma.chat.enums.UserStatus;
import com.diploma.chat.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    List<User> findAllByStatus(UserStatus status);
}
