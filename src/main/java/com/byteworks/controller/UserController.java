package com.byteworks.controller;

import com.byteworks.Response.UserResponse;
import com.byteworks.dto.UserDto;
import com.byteworks.model.User;
import com.byteworks.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Kelvin
 *
 */
@RestController
@RequestMapping("/api/v1")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    @ApiOperation(value = "User Login", response = String.class)
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestParam("email") String email, @RequestParam("password") String password ) {

        return userService.loginUser(email, password);

    }

    @ApiOperation(value = "User Registration", response = User.class)
    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody UserDto userDto) {

        UserResponse userResponse = new UserResponse();

        User user = userService.saveNewUser(userDto);

        if (user == null) {
            userResponse.setUserId(null);
            userResponse.setResponseMessage("Something went wrong while creating your profile. Please try again.");
            return new ResponseEntity<>(userResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        userResponse.setUserId(user.getId());
        userResponse.setResponseMessage("Your profile was created successfully.");

        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Fetch all Users", response = List.class)
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getAllUsers() {

        return userService.findAllUsers();

    }

    @ApiOperation(value = "Fetch User By ID", response = User.class)
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
    public User getUserById(@PathVariable Long id) {

        return userService.findById(id);

    }

}
