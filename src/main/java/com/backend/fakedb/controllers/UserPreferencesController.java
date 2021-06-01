package com.backend.fakedb.controllers;

import com.backend.fakedb.entities.ProviderEntity;
import com.backend.fakedb.entities.UserPreferencesEntity;
import com.backend.fakedb.services.UserPreferencesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("preferences")
public class UserPreferencesController {

    private final UserPreferencesService upService;

    @Autowired
    public UserPreferencesController(UserPreferencesService upService) {
        this.upService = upService;
    }

    @GetMapping("/getAll")
    public List<UserPreferencesEntity> getPreferences() {
        return upService.getPreferences();
    }

    /**
     * Public method that subscribes the user to a list of providers specified by their ids
     * @param auth_id user's authentication id
     * @param token user's authentication token
     * @param uid user's id
     * @param providerIDs provider id list
     * @return true, if user has been subscribed to all providers in the specified list and false, otherwise
     */
    @PostMapping("/subscribeUserToProviders")
    public boolean subscribeUserToProviders(
            @RequestHeader(name = "X-Auth-User") Integer auth_id,
            @RequestHeader(name = "X-Auth-Token") String token,
            @RequestParam(name = "uid", required = true) int uid,
            @RequestBody List<Integer> providerIDs) {
        if (auth_id == uid) {
            return upService.subscribeUserToProviders(auth_id, token, uid, providerIDs);
        }
        return false;
    }

    /**
     * public method for checking if a user is subscribed to a specified provider
     * @param auth_id user's authentification id
     * @param token user's authentification token
     * @param uid user's id
     * @param prov_id provider's id
     * @return true, if the user is subscribed to the specified provider and false, otherwise
     */
    @GetMapping("/isSubscribed")
    public int isSubscribed(
            @RequestHeader(name = "X-Auth-User") Integer auth_id,
            @RequestHeader(name = "X-Auth-Token") String token,
            @RequestParam(name = "uid", required = true) int uid,
            @RequestParam(name = "prov_id", required = true) int prov_id) {
        return upService.isSubscribed(auth_id, token, uid, prov_id);
    }

    /**
     * public method returns a list of provider entities that a specified user is subscribed to
     * @param auth_id user's authentification id
     * @param token user's authentification token
     * @param uid user's id
     * @param skip skip a specified number of entries
     * @param count return a specified number of providers
     * @return a list of provider entities that a specified user is subscribed to
     */
    @GetMapping("/getByUserId")
    public List<ProviderEntity> getProviderListForUser(
            @RequestHeader(name = "X-Auth-User") Integer auth_id,
            @RequestHeader(name = "X-Auth-Token") String token,
            @RequestParam(name = "uid", required = true) int uid,
            @RequestParam(name = "skip", required = false, defaultValue = "0") int skip,
            @RequestParam(name = "count", required = false, defaultValue = "100") int count) {
        return upService.getProviderListForUser(auth_id, token, uid, skip, count);
    }

    /**
     * public method for updating the subscription status for a specified user and provider
     * @param auth_id user's authentification id
     * @param token user's authentification token
     * @param uid user's id
     * @param prov_id provider's id
     * @param status true, if the user wants to be subscribed to that provider and false, otherwise
     * @return true, if the process was successful and false, otherwise
     */
    @PutMapping("/updateSubscriptionStatus")
    public boolean updateSubscriptionStatus(
            @RequestHeader(name = "X-Auth-User") Integer auth_id,
            @RequestHeader(name = "X-Auth-Token") String token,
            @RequestParam(name = "uid", required = true) int uid,
            @RequestParam(name = "prov_id", required = true) int prov_id,
            @RequestParam(name = "status", required = true) boolean status) {
        if (auth_id.equals(uid)) {
            return upService.updateSubscriptionStatus(auth_id, token, uid, prov_id, status);
        }
        return false;
    }
}

