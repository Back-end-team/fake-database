package com.backend.fakedb.services;

import com.backend.fakedb.entities.ProviderEntity;
import com.backend.fakedb.repositories.SessionRepository;
import com.backend.fakedb.utilities.IngestionLinker;
import com.backend.fakedb.utilities.IntWrapper;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProviderService {

    private final SessionRepository sessionRepository;
    private final IngestionLinker ingestionLinker = new IngestionLinker();

    @Autowired
    public ProviderService(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    public List<ProviderEntity> getAll() {
        return ingestionLinker.providerGetAll();
    }

    /**
     * Public method that returns a List with all available providers.
     * @param auth_id user's authentication id
     * @param token user's authentication token
     * @return a List containing all providers
     */
    public List<ProviderEntity> getAll(int auth_id, String token) {

        if (sessionRepository.findAll().stream().anyMatch(session -> session.getUser_id() == auth_id && session.getToken().equals(token))) {
            return ingestionLinker.providerGetAll();
        }
        return null;
    }

    /**
     * Public method that returns a List of ProviderEntity objects of size 'c' and skipping 's' rows.
     * @param auth_id user's authentification id
     * @param token user's authentification token
     * @param s the amount of rows to skip (a number over or equal to zero)
     * @param c the number of elements in the list (a number over zero)
     * @return a List containing the specified values (if they exists). If the given skip and count numbers overpass the existing rows, null items are added in the list.
     */
    public List<ProviderEntity> getInterval(int auth_id, String token, int s, int c) {

        // User is not authenticated
        if (sessionRepository.findAll().stream().noneMatch(session -> session.getUser_id() == auth_id && session.getToken().equals(token))) {
            return null;
        }

        return ingestionLinker.providerGetInterval(s, c);
    }

    /**
     * Public method for getting the amount of providers in the database.
     * @param auth_id user's authentication id
     * @param token user's authentication token
     * @return the amount of providers in the database in a IntWrapper class
     */
    public IntWrapper getCount(int auth_id, String token) {
        if (sessionRepository.findAll().stream().anyMatch(session -> session.getUser_id() == auth_id && session.getToken().equals(token))) {
            return ingestionLinker.providerGetCount();
        }
        return null;
    }

    /**
     * Public method for getting the amount of providers in the database that contain the given string.
     * @param auth_id user's authentication id
     * @param token user's authentication token
     * @param query string to be contained in the name of the provider
     * @return an IntWrapper containing the number of providers
     */
    public IntWrapper searchCount(int auth_id, String token, String query) {
        if (sessionRepository.findAll().stream().anyMatch(session -> session.getUser_id() == auth_id && session.getToken().equals(token))) {
            return ingestionLinker.providerSearchCount(query);
        }
        return null;
    }

    /**
     * Public method for getting the amount of providers in the database that contain the given string.
     * If the given skip and count parameters exceed the database limit, null objects will be added to the list.
     * @param auth_id user's authentication id
     * @param token user's authentication token
     * @param query string that needs to be contained by the provider's name
     * @param s the amount of providers to be skipped (a number greater than or equal to zero)
     * @param c the amount of providers to be returned (a number greater that zero)
     * @return a list with the objects requested
     */
    public List<ProviderEntity> search(int auth_id, String token, String query, int s, int c) {
        if (sessionRepository.findAll().stream().noneMatch(session -> session.getUser_id() == auth_id && session.getToken().equals(token))) {
            return null;
        }
        return ingestionLinker.providerSearch(query, s, c);
    }

    public Optional<ProviderEntity> getById(Integer auth_id, String token, int prov_id) {
        if (sessionRepository.findAll().stream().noneMatch(session -> session.getUser_id() == auth_id && session.getToken().equals(token))) {
            return Optional.empty();
        }
        return getAll(auth_id, token).stream()
                .filter(a -> a.getId() == prov_id)
                .findFirst();
    }
}
