package com.example.application.services;

import com.example.application.data.Role;
import com.example.application.data.User;
import com.example.application.data.UserRepository;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> get(Long id) {
        return repository.findById(id);
    }

    public User update(User entity) {
        return repository.save(entity);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Page<User> list(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<User> list(Pageable pageable, Specification<User> filter) {
        return repository.findAll(filter, pageable);
    }

    public boolean usernameExists(String username) {
        return repository.findByUsername(username).isPresent();
    }

    public void saveUser(String username, String name, String password) {
        if (usernameExists(username)) {
            throw new IllegalArgumentException("Username already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setName(name);
        user.setHashedPassword(passwordEncoder.encode(password));
        user.setRoles(Collections.singleton(Role.USER));  // Default role
        user.setProfilePicture(null);  // Default profile picture

        repository.save(user);
    }

    public int count() {
        return (int) repository.count();
    }
}