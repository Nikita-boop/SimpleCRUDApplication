package ru.kata.spring.boot_security.demo.service;

import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<User> allUsers() {
        return IteratorUtils.toList(userRepository.findAll().iterator());
    }

    @Override
    @Transactional
    public void add(User user) {
        user.setPassword((new BCryptPasswordEncoder()).encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void edit(User user) {
        if (!(user.getPassword().equals(getUser(user.getId()).getPassword()))) {
            user.setPassword((new BCryptPasswordEncoder()).encode(user.getPassword()));
        }
        userRepository.save(user);
    }

    @Override
    public User getUser(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public List<Role> getRoles() {
        return IteratorUtils.toList(roleRepository.findAll().iterator());
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);

        user.getAuthorities().size();

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
