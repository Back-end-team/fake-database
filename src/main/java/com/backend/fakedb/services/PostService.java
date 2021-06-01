package com.backend.fakedb.services;

import com.backend.fakedb.entities.PostEntity;
import com.backend.fakedb.entities.ProviderEntity;
import com.backend.fakedb.repositories.SessionRepository;
import com.backend.fakedb.utilities.IngestionLinker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private IngestionLinker ingestionLinker;
    private final SessionRepository sessionRepository;
    private final UserPreferencesService userService;

    @Autowired
    public PostService(SessionRepository sessionRepository, UserPreferencesService userService) {
        this.sessionRepository = sessionRepository;
        this.userService = userService;
        ingestionLinker = new IngestionLinker();
    }

    // Only used in unit tests in order to inject a mock into the PostService class
    // TODO: In order for this method to not be exposed, simulating a friend class
    //       that would instead set the IngestionLinker reference would be better
    public void setLinker(IngestionLinker injectionLinker) {
        this.ingestionLinker = injectionLinker;
    }

    /**
     * Public method for getting a list of posts from the ingestion system.
     * If the given parameters exceed the database number of existing rows, null objects will be added to the list.
     * @param s the amount of rows to skip (must be a number equal or greater than zero)
     * @param auth_id user's authentification id
     * @param token user's authentification token
     * @param c the number of posts to return (must be a number greater than zero)
     * @return a list of size 'c' of PostEntity objects
     */
    public List<PostEntity> getInterval(int auth_id, String token, int s, int c, String order, String query, String date) {


        if (sessionRepository.findAll().stream().anyMatch(session -> session.getUser_id() == auth_id && session.getToken().equals(token))) {

            List<ProviderEntity> providerList = userService.getProviderListForUser(auth_id, token, auth_id, 0, 10);

            StringBuilder sb = new StringBuilder();

            for(var provider : providerList)
            {
                sb.append("list=").append(provider.getId()).append("&");
            }

            System.out.println(sb.toString());
            return ingestionLinker.getInterval(s, c, sb.toString(), order, query, date);
        }

        return null;
    }

    /**
     * Public method which returns a list of post from a specific provider.
     * If the given parameters exceed the database number of existing rows, null objects will be added to the list.
     * @param auth_id user's authentification id
     * @param token user's authentification token
     * @param provider_id the provider from which to get the posts
     * @param s the amount of rows to skip (must be a number equal or greater than zero)
     * @param c the number of posts to return (must be a number greater than zero)
     * @return a list containing the requested elements.
     */
    public List<PostEntity> getIntervalByProvider(int auth_id, String token, int provider_id, int s, int c) {
        if (sessionRepository.findAll().stream().anyMatch(session -> session.getUser_id() == auth_id && session.getToken().equals(token))) {
            return ingestionLinker.getIntervalByProvider(provider_id, s, c);
        }
        return null;
    }

}
