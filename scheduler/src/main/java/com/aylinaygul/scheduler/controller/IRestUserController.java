package com.aylinaygul.scheduler.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.aylinaygul.scheduler.model.User;

public interface IRestUserController {

    List<User> getAllUsers();

    Optional<User> getUserById(UUID id);

    Optional<User> getUserbyEmail(String email);

    Optional<User> getUserbyUsername(String username);

    User saveUser(User user);

    User updateUser(User user);

    void deleteUser(UUID id);

}
