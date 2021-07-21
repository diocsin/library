package com.example.library.library.service;

import com.example.library.library.model.Role;
import com.example.library.library.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAllUsers();

    List<Role> getAllRoles();

    User createNewUser(User user);

    User updateUser(User user);

    User getUserById(Long pid);

    Optional<User> getUserByLogin(String login);

    void deleteUserById(Long pid);
    
    List<User> filterUser(String filterText);

}
