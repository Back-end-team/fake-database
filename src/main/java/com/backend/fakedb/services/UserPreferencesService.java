package com.backend.fakedb.services;

import com.backend.fakedb.entities.ProviderEntity;
import com.backend.fakedb.entities.UserPreferencesEntity;
import com.backend.fakedb.repositories.SessionRepository;
import com.backend.fakedb.repositories.UserPreferencesRepository;
import com.backend.fakedb.repositories.UserRepository;
import com.backend.fakedb.utilities.IngestionLinker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserPreferencesService {

    private final UserPreferencesRepository upRepository;
    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;
    private final IngestionLinker ingestionLinker;

    @Autowired
    public UserPreferencesService(UserPreferencesRepository upRepository, UserRepository userRepository, SessionRepository sessionRepository) {
        this.upRepository = upRepository;
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        ingestionLinker = new IngestionLinker();
    }

    /**
     * public method for getting all the user's preferences
     * @return a list of UserPreferencesEntities
     */
    public List<UserPreferencesEntity> getPreferences() {
        return upRepository.findAll();
    }

    /**
     * Public method that subscribes a specified user to a list of providers
     * @param auth_id user's authentication id
     * @param token user's authentication token
     * @param uid user's id
     * @param providerIDs list of provider's ids
     * @return true, if the process has been successful and false, otherwise
     */
    public boolean subscribeUserToProviders(int auth_id, String token, int uid, List<Integer> providerIDs) {
        if (sessionRepository.findAll().stream().noneMatch(session -> session.getUser_id() == auth_id && session.getToken().equals(token))) {
            return false;
        }

        var providerList = ingestionLinker.providerGetAll();

        for (var prov_id : providerIDs) {
            var user = userRepository.findById(uid);
            var prov = providerList.stream().filter(p -> p.getId().equals(prov_id)).findFirst();
            if (user.isEmpty() || prov.isEmpty())
                return false;
            upRepository.save(new UserPreferencesEntity(user.get().getId(), prov.get().getId(), true));
        }
        return true;
    }

    /**
     * Public method for checking if a specified user is subscribed to a specified provider
     * @param auth_id user's authentication id
     * @param token user's authentication token
     * @param uid user's id
     * @param prov_id provider's id
     * @return true, if the user is subscribed to the specified provider and false, otherwise
     */
    public int isSubscribed(int auth_id, String token, int uid, int prov_id) {
        if (sessionRepository.findAll().stream().noneMatch(session -> session.getUser_id() == auth_id && session.getToken().equals(token))) {
            return -1;
        }

        var user = userRepository.findById(uid);
        if (user.isEmpty()) {
            return -2;
        }

        var provider = ingestionLinker.providerGetAll().stream().filter(p -> p.getId().equals(prov_id)).findFirst();
        if (provider.isEmpty()) {
            return -3;
        }

        var result = upRepository.findAll().stream().filter(p -> p.getUserId().equals(uid) && p.getProviderId().equals(prov_id)).findFirst();
        if (result.isEmpty()) {
            return -4;
        }
        return result.get().isSubscribed() ? 1 : 0;
    }

    /**
     * Public method for getting the providers a specified user is subscribed to
     * @param auth_id user's authentication id
     * @param token user's authentication token
     * @param uid user's id
     * @param skip skip a specified number of entries
     * @param count the specified number of entries the method returns
     * @return a list of ProviderEntities (the providers the user is subscribed to)
     */
    public List<ProviderEntity> getProviderListForUser(int auth_id, String token, int uid, int skip, int count) {
        if (sessionRepository.findAll().stream().noneMatch(session -> session.getUser_id() == auth_id && session.getToken().equals(token))) {
            return null;
        }

        var providerIDListForCurrentUser = upRepository.findAll().stream()
                .filter(UserPreferencesEntity::isSubscribed)
                .filter(entry -> entry.getUserId() == uid)
                .map(UserPreferencesEntity::getProviderId)
                .collect(Collectors.toSet());

        return ingestionLinker.providerGetAll().stream()
                .filter(entry -> providerIDListForCurrentUser.contains(entry.getId()))
                .skip(skip)
                .limit(count)
                .collect(Collectors.toList());
    }

    /**
     * Public method that updates the subscription status of a specified user for a specified provider
     * @param auth_id user's authentication id
     * @param token user's authentication token
     * @param uid user's id
     * @param prov_id provider's id
     * @param status subscription status (true, if the user wants to be subscribed and false, otherwise)
     * @return true, if the process was successful and false, otherwise
     */
    @Transactional
    public boolean updateSubscriptionStatus(int auth_id, String token, int uid, int prov_id, boolean status) {
        if (sessionRepository.findAll().stream().noneMatch(session -> session.getUser_id() == auth_id && session.getToken().equals(token))) {
            return false;
        }

        var user = userRepository.findById(uid);
        var provider = ingestionLinker.providerGetAll().stream().filter(p -> p.getId().equals(prov_id)).findFirst();
        if (user.isEmpty() || provider.isEmpty()) {
            return false;
        }

        upRepository.update(uid, prov_id, status);
        return true;
    }

    /**
     * Public method for adding an entry into the UserPreferences table
     * @param uid the user ID
     * @param prov_id the provider ID
     * @param status true if the user is subscribed to this particular provider, false otherwise
     */
    public void addSubscriptionStatus(int uid, int prov_id, boolean status) {
        var user = userRepository.findById(uid);
        var provider = ingestionLinker.providerGetAll().stream().filter(p -> p.getId().equals(prov_id)).findFirst();
        if (user.isEmpty() || provider.isEmpty()) {
            return;
        }

        upRepository.save(new UserPreferencesEntity(uid, prov_id, status));
    }
}
