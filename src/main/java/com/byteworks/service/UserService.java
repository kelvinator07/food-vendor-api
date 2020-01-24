package com.byteworks.service;

import com.byteworks.dto.UserDto;
import com.byteworks.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();

    User findById(Long id);

    User findByEmail(String email);

    User saveNewUser(UserDto userDto);

    ResponseEntity loginUser(String email, String password);

    User save(User user);

    User findByUsername(String username);
}
