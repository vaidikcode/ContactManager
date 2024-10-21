package com.scm.Services;

import com.scm.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface UserServices {
    User saveUser(User user);
    Optional<User> getUserById(String id);
    Optional<User> updateUser(User user);
    void deleteUser(String id);
    boolean isUserExist(String id);
    boolean isUserExistbyEmail(String email);
    List<User> getAllUsers();
    User getByEmail(String username);
}
