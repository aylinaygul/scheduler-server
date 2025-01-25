package com.aylinaygul.scheduler.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.aylinaygul.scheduler.model.entity.User;

public interface IUserService {

    List<User> getAllUsers();

    Optional<User> getUserById(UUID id);

    Optional<User> getUserbyEmail(String email);

    User saveUser(User user);

    User updateUser(User user);

    void deleteUser(UUID id);
}
