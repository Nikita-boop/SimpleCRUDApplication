package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

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
