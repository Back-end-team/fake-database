package com.backend.fakedb.controllers;

import com.backend.fakedb.entities.UserEntity;
import com.backend.fakedb.services.UserPreferencesService;
import com.backend.fakedb.services.UserService;
import com.backend.fakedb.utilities.LoginResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * public method for getting all the users in the database
     * @return a list of UserEntities (all the users in the database)
     */
    @GetMapping("/getAll")
    public List<UserEntity> getUsers() {
        return userService.getUsers();
    }

    /**
     * public method for finding user by a specified id
     * @param id the id of the user we're trying to find
     * @return UserEntity that has the specified id
     */
    @GetMapping("/get")
    public Optional<UserEntity> getUserById(@RequestParam(name = "id", required = true) Integer id) {
        return userService.getUserById(id);
    }

    /**
     * public method for registering a user in the database
     * @param user user we want to register
     * @return true, if the process was successful and false, otherwise
     */
    @PostMapping("/register")
    public boolean registerUser(@RequestBody UserEntity user) {
        return userService.registerUser(user);
    }

    /**
     * public method to log a user in
     * @param username user's username
     * @param password user's password
     * @return LoginResponseWrapper containing the user's id and the authentification token
     */
    @PostMapping("/login")
    public Optional<LoginResponseWrapper> loginUser(
            @RequestParam(name = "username", required = true) String username,
            @RequestParam(name = "password", required = true) String password) {
        return userService.loginUser(username, password);
    }

    /**
     * public method for deleting a user by their id
     * @param id user's id
     * @return true, if the process has been successful and false, otherwise
     */
    @DeleteMapping("/delete")
    public boolean deleteUser(@RequestParam(name = "id", required = true) Integer id) {
        return userService.deleteUser(id);
    }

    /**
     * public method that logs the user out
     * @param auth_id user's authentification id
     * @param token user's authentification token
     */
    @DeleteMapping("/logout")
    public void logout(
            @RequestHeader(name = "X-Auth-User") Integer auth_id,
            @RequestHeader(name = "X-Auth-Token") String token) {
        userService.logout(auth_id, token);
    }

    /**
     * public method that allows the user to change their password, avatar or bio
     * @param auth_id user's authentification id
     * @param token user's authentification token
     * @param id user's id
     * @param password user's new password
     * @param avatar user's new avatar
     * @param bio user's new bio
     * @return true, if the process has been successful and false, otherwise
     */
    @PutMapping("/update")
    public boolean updateUser(
            @RequestHeader(name = "X-Auth-User") Integer auth_id,
            @RequestHeader(name = "X-Auth-Token") String token,
            @RequestParam(name = "id") Integer id,
            @RequestParam(name = "password", required = false, defaultValue = "") String password,
            @RequestParam(name = "avatar", required = false, defaultValue = "") String avatar,
            @RequestParam(name = "bio", required = false, defaultValue = "") String bio) {
        if (auth_id.equals(id)) {
            return userService.updateUser(auth_id, token, id, password, avatar, bio);
        }
        return false;
    }
}
