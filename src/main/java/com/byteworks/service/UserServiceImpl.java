package com.byteworks.service;

import com.byteworks.Response.UserResponse;
import com.byteworks.dto.UserDto;
import com.byteworks.exception.UserAlreadyExistsException;
import com.byteworks.exception.UserNotFoundException;
import com.byteworks.model.Role;
import com.byteworks.model.User;
import com.byteworks.repository.RoleRepository;
import com.byteworks.repository.UserRepository;
import com.byteworks.util.SecurityUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Kelvin
 *
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);

        if (!user.isPresent()) {
            throw new UserNotFoundException("User Not Found for id: " + id);
        }

        return user.get();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User saveNewUser(UserDto userDto) {

        if (findByEmail(userDto.getEmail()) != null) {
            throw new UserAlreadyExistsException("User already exists with email: !" + userDto.getEmail());
        }

        User user = new User();

        user.setUsername(userDto.getUserName());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setCreatedDate(new Date());

        String encryptedPassword = SecurityUtility.passwordEncoder().encode(userDto.getPassword());
        user.setPassword(encryptedPassword);

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(new Role("DEV"));
        user.setRoles(userRoles);

        User savedUser = userRepository.save(user);

        savedUser.setPassword(null);

        return savedUser;
    }

    @Override
    public ResponseEntity<?> loginUser(String email, String password) {

        User user = new User();

        UserResponse userResponse = new UserResponse();

        try {
            user = userRepository.findByEmail(email);
        }catch (Exception e) {
            LOG.info("An error occurred " + e);
        }

        if (user == null) {
            //userResponse.setUserId(null);
            userResponse.setResponseMessage("User Not Found!");
            return new ResponseEntity<>(userResponse, HttpStatus.NOT_FOUND);
        }

        String encodedPassword = SecurityUtility.passwordEncoder().encode(password);

        if (!(email.equals(user.getEmail()) && encodedPassword.equals(user.getPassword())) ) {
            //userResponse.setUserId(null);
            userResponse.setResponseMessage("User Details Incorrect!");
            return new ResponseEntity<>(userResponse, HttpStatus.BAD_REQUEST);
        }

        userResponse.setUserId(user.getId());
        userResponse.setResponseMessage("Login Successful!");
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

}
