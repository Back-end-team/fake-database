package com.backend.fakedb.services;

import com.backend.fakedb.entities.ProviderEntity;
import com.backend.fakedb.entities.SessionEntity;
import com.backend.fakedb.entities.UserEntity;
import com.backend.fakedb.repositories.SessionRepository;
import com.backend.fakedb.repositories.UserRepository;
import com.backend.fakedb.utilities.LoginResponseWrapper;
import org.apache.catalina.User;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserPreferencesService upService;
    private final ProviderService providerService;
    private final SessionRepository sessionRepository;

    @Autowired
    public UserService(UserRepository userRepository, UserPreferencesService upService, ProviderService providerService, SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.upService = upService;
        this.providerService = providerService;
        this.sessionRepository = sessionRepository;
    }

    /**
     * public method for getting all the users in the database
     * @return a list of UserEntities (all the users in the database)
     */
    public List<UserEntity> getUsers() {
        var users = userRepository.findAll();
        users.forEach(u -> u.setPasswordHash(""));
        return users;
    }

    /**
     * public method for finding a user by a specified id
     * @param id the id of the user we're trying to find
     * @return a UserEntity (the user whose id the specified one belongs to)
     */
    public Optional<UserEntity> getUserById(Integer id) {
        var maybeUser = userRepository.findById(id);
        if (maybeUser.isPresent()) {
            var user = maybeUser.get();
            user.setPasswordHash("");
            return Optional.of(user);
        }
        return Optional.empty();
    }

    /**
     * publc method for registering a user in the database
     * @param user the user we want to register
     * @return true, if the process has been successful and false, otherwise
     */
    public boolean registerUser(UserEntity user) {
        var maybeUser = userRepository.findAll().stream()
                .filter(u -> u.getUsername().equals(user.getUsername()))
                .findFirst();
        if (maybeUser.isEmpty()) {
            String plaintextPassword = user.getPasswordHash(); // at this point, the password is still plaintext; it hasn't been hashed yet
            user.setPasswordHash(DigestUtils.sha256Hex(plaintextPassword));
            userRepository.save(user);

            // Add this user (alongside all the providers) into the UserPreferences table
            List<ProviderEntity> providerList = providerService.getAll();
            for (ProviderEntity p : providerList) {
                upService.addSubscriptionStatus(user.getId(), p.getId(), false);
            }
            return true;
        }
        return false;
    }

    /**
     * public method for deleting a specified user from the database
     * @param id the id that belongs to the user we want to delete
     * @return true, if the process has been successful and false, otherwise
     */
    public boolean deleteUser(Integer id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * public method for changing the password, avatar and/or bio of a specified user
     * @param auth_id user's authentification id
     * @param token user's authentification token
     * @param id user's id
     * @param password user's new password
     * @param avatar user's new avatar
     * @param bio user's new bio
     * @return true, if the process has been successful and false, otherwise
     */
    @Transactional
    public boolean updateUser(Integer auth_id, String token, Integer id, String password, String avatar, String bio) {
        var maybeUser = userRepository.findById(id);
        if (maybeUser.isPresent()) {
            if (sessionRepository.findAll().stream().anyMatch(s -> s.getUser_id() == auth_id && s.getToken().equals(token))) {
                String newPass = password.equals("") ? maybeUser.get().getPasswordHash() : DigestUtils.sha256Hex(password);
                String newAvatar = avatar.equals("") ? maybeUser.get().getAvatarUrl() : avatar;
                String newBio = bio.equals("") ? maybeUser.get().getBio() : bio;
                userRepository.update(id, newPass, newAvatar, newBio);
                return true;
            }
        }
        return false;
    }

    /**
     * public method that logs a specified user in the system
     * @param username user's username
     * @param password user's password
     * @return LoginResponseWrapper containing the user's id and the authentification token
     */
    public Optional<LoginResponseWrapper> loginUser(String username, String password) {
        var maybeUser = userRepository.findAll().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();

        if (maybeUser.isPresent()) {
            var user = maybeUser.get();
            String hash = DigestUtils.sha256Hex(password);
            if (!hash.equals(user.getPasswordHash())) {
                return Optional.empty();
            }

            var maybeSession = sessionRepository.findAll().stream()
                    .filter(s -> s.getUser_id() == user.getId())
                    .findFirst();
            if (maybeSession.isPresent()) {
                return Optional.of(new LoginResponseWrapper(user.getId(), maybeSession.get().getToken()));
            } else {
                String token = RandomStringUtils.randomAlphanumeric(64);
                // var newSession = new SessionEntity((int) (sessionRepository.count() + 1), user.getId(), token);
                var newSession = new SessionEntity(user.getId(), token);
                sessionRepository.save(newSession);
                return Optional.of(new LoginResponseWrapper(user.getId(), token));
            }
        }
        return Optional.empty();
    }

    /**
     * public method that logs a specified user out 
     * @param auth_id user's authentification id 
     * @param token user's authentification token 
     */
    public void logout(Integer auth_id, String token) {
        var maybeSession = sessionRepository.findAll().stream()
                .filter(s -> s.getUser_id() == auth_id && s.getToken().equals(token))
                .findFirst();
        if (maybeSession.isPresent()) {
            sessionRepository.deleteById(maybeSession.get().getId());
        }
    }
}
