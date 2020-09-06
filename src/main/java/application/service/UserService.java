package application.service;

import application.model.security.User;

import java.util.List;

public interface UserService {
    boolean isExist(User user);
    void create(User user);
    User findByUsername(String username);
    User findUserById(Long userId);
    List<String> findAllUsernames();


}