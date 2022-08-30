package ru.kata.spring.bootstrap.demo.service;

import ru.kata.spring.bootstrap.demo.model.Role;
import ru.kata.spring.bootstrap.demo.model.User;

import java.util.List;

public interface UserService {
    List<User> allUsers();
    void add(User user);
    void delete(Integer id);
    void edit(User user);
    User getUser(Integer id);
    User getUserByEmail(String email);
    List<Role> getRoles();
}
