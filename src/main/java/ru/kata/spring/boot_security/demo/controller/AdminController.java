package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String allUsers(Model model) {
        List<User> users = userService.allUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/new")
    public String createUserForm(User user, Model model) {
        List<Role> roles = userService.getRoles();
        model.addAttribute("roles", roles);
        return "new";
    }

    @PostMapping("/new")
    public String addUser(User user) {
        userService.add(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        User user = userService.getUser(id);
        userService.delete(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/edit/{id}")
    public String updateUserForm(@PathVariable("id") Integer id, Model model) {
        User user = userService.getUser(id);
        List<Role> roles = userService.getRoles();
        model.addAttribute("roles", roles);
        model.addAttribute("user", user);
        return "edit";
    }

    @PostMapping("/edit")
    public String updateUser(User user) {
        userService.edit(user);
        return "redirect:/admin/users";
    }

    @GetMapping ("/view/{id}")
    public String viewUserDetails(@PathVariable Integer id, Model model) {
        User user = userService.getUser(id);
        model.addAttribute("user", user);
        return "user";
    }
}
