package com.backend.fakedb.utilities;

import com.backend.fakedb.entities.*;
import com.backend.fakedb.services.UserPreferencesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that handles the communication between the ingestion system and this app.
 *
 * It requests the posts from the ingestion system then it splits every post into two:
 * an AiEntity (which will be sent to the AI module) and a PostEntity which will be constructed
 * with information from the ingestion and the examination result (how likely it is to be fake news).
 */
public class IngestionLinker {

    RestTemplate ingestion;

    public IngestionLinker() {
        ingestion = new RestTemplate();
    }

    /**
     * Public method for getting a list of PostEntity objects from the posts database.
     * If the given arguments surpass the amount of rows in the database, null objects will be added to the list.
     * @param skip the amount of rows to skip (must be a number greater than or equal to zero)
     * @param count the amount of rows to receive (must be a number greater than zero)
     * @param credentials
     * @return the specified list
     */
    public List<PostEntity> getInterval(int skip, int count, String credentials, String order, String query, String date) {
        if (skip < 0 || count < 1) {
            return null;
        }

        // The response will return posts from the database, according to the skip and count parameters
        StringBuilder sb = new StringBuilder("https://fake-news-detection-ingestion.herokuapp.com/v1/api/news/getIntervalByArray?");
        sb.append(credentials).append("skip=").append(skip).append("&count=").append(count);
        if(order != null) {
            sb.append("&order=").append(order);
        }
        if(query != null) {
            sb.append("&query=").append(query);
        }
        if(date != null) {
            sb.append("&date=").append(date);
        }
        ResponseEntity<IngestionEntity[]> response =
                ingestion.getForEntity(sb.toString(), IngestionEntity[].class);

        IngestionEntity[] ingestionArray = response.getBody();

        // If the list is null, don't continue.
        if (ingestionArray == null) {
            return null;
        }

        return getPostEntities(ingestionArray);
//        List<PostEntity> postEntityList = new ArrayList<>(ingestionArray.length);
//        for (IngestionEntity i : ingestionArray) {
//            postEntityList.add(convertToPostEntity(i, "dont-believe-this"));
//        }
//        return postEntityList;

    }

    /**
     * Public method for getting a list of posts by a specific provider.
     *  If the given arguments surpass the amount of rows in the database, null objects will be added to the list.
     * @param providerId the provider ID to get posts from
     * @param skip the amount of posts to be skipped (needs to be greater than or equal to zero)
     * @param count the number of posts to receive (needs to be greater that zero)
     * @return the specified list
     */
    public List<PostEntity> getIntervalByProvider(int providerId, int skip, int count) {
        if (skip < 0 || count < 1) {
            return null;
        }

        // The response will return posts from the database provided by provider_id, according to the skip and count parameters
        ResponseEntity<IngestionEntity[]> response =
                ingestion.getForEntity("https://fake-news-detection-ingestion.herokuapp.com/v1/api/news/getIntervalByProvider?provider_id=" + providerId + "&skip=" + skip + "&count=" + count, IngestionEntity[].class);
        IngestionEntity[] ingestionArray = response.getBody();

        // If the list is null, don't continue.
        if (ingestionArray == null) {
            return null;
        }

        return getPostEntities(ingestionArray);
//        List<PostEntity> postEntityList = new ArrayList<>(ingestionArray.length);
//        for (IngestionEntity i : ingestionArray) {
//            postEntityList.add(convertToPostEntity(i, "dont-believe-this"));
//        }
//        return postEntityList;
    }

    /**
     * Public method for getting a list of all the ProviderEntity objects from the posts database.
     * @return the list
     * @deprecated method no longer needed (24 May 2021)
     */
    public List<ProviderEntity> providerGetAll() {

        // The response will return all providers from the database
        ResponseEntity<ProviderEntity[]> response =
                ingestion.getForEntity("https://fake-news-detection-ingestion.herokuapp.com/v1/api/news/getAll", ProviderEntity[].class);
        ProviderEntity[] providerArray = response.getBody();

        assert providerArray != null;
        return new ArrayList<>(Arrays.asList(providerArray));
    }

    /**
     * Public method for getting a list of ProviderEntity objects from the posts database.
     * If the given arguments surpass the amount of rows in the database, null objects will be added to the list.
     * @param skip the amount of rows to skip (must be a number greater than or equal to zero)
     * @param count the amount of rows to receive (must be a number greater than zero)
     * @return the specified list
     */
    public List<ProviderEntity> providerGetInterval(int skip, int count) {
        if (skip < 0 || count < 1) {
            return null;
        }

        // The response will return providers from the database, according to the skip and count parameters
        ResponseEntity<ProviderEntity[]> response =
                ingestion.getForEntity("https://fake-news-detection-ingestion.herokuapp.com/v1/api/news/providers/getInterval?skip=" + skip + "&count=" + count, ProviderEntity[].class);
        ProviderEntity[] providerArray = response.getBody();

        // If the list is null, don't continue.
        if (providerArray == null) {
            return null;
        }

        return new ArrayList<>(Arrays.asList(providerArray));
    }

    /**
     * Public method for getting count of all the providers.
     * @return the count
     */
    public IntWrapper providerGetCount() {

        // The response will return all providers from the database
        ResponseEntity<IntWrapper> response =
                ingestion.getForEntity("https://fake-news-detection-ingestion.herokuapp.com/v1/api/news/getCount", IntWrapper.class);

        return response.getBody();
    }

    /**
     * public method for getting the count for providers matching the query
     * @return  the count
     * */

    public IntWrapper providerSearchCount(String queryToBeContained) {
        ResponseEntity<IntWrapper> response =
                ingestion.getForEntity("https://fake-news-detection-ingestion.herokuapp.com/v1/api/news/searchCount?query=" + queryToBeContained, IntWrapper.class);
        return response.getBody();
    }

    /**
     * Public method for getting a list of ProviderEntity objects from the posts database by specified filters.
     * If the given arguments surpass the amount of rows in the database, null objects will be added to the list.
     * @param query filters for the searching of providers
     * @param skip the amount of rows to skip (must be a number greater than or equal to zero)
     * @param count the amount of rows to receive (must be a number greater than zero)
     * @return the specified list
     */

    public List<ProviderEntity> providerSearch(String query, int skip, int count) {
        if (skip < 0 || count < 1) {
            return null;
        }

        ResponseEntity<ProviderEntity[]> response =
                ingestion.getForEntity("https://fake-news-detection-ingestion.herokuapp.com/v1/api/news/search?query="+ query + "&skip=" + skip + "&count=" + count, ProviderEntity[].class);
        ProviderEntity[] providerArray = response.getBody();

        assert providerArray != null;
        return new ArrayList<>(Arrays.asList(providerArray));
    }

    /**
     * Private method for converting posts coming from Ingestion.
     * @param ingestionArray the array of posts
     * @return a list of PostEntity objects
     */
    private List<PostEntity> getPostEntities(IngestionEntity[] ingestionArray) {
        List<PostEntity> responseList = new ArrayList<>(ingestionArray.length);

        for (int i = 0; i < ingestionArray.length; i++) {
            // We need to sent an AiEntity to the AI module and get the associated result
            ScoreResult responseScore = ingestion.postForObject("https://ai-communication-module.herokuapp.com/ai-module/", convertToAiEntity(ingestionArray[i]), ScoreResult.class);

            // We need to build a PostEntity with the associated score and information
            assert responseScore != null;
            responseList.add(i, convertToPostEntity(ingestionArray[i], responseScore.getScore()));
        }
        return responseList;
    }

    /**
     * Private method that converts a given ingestion entity to a AI entity.
     * @param ingestionEntity the entity to be converted
     * @return the AI entity
     */
    private AiEntity convertToAiEntity(IngestionEntity ingestionEntity) {
        return new AiEntity(ingestionEntity.getId().hashCode(),
                ingestionEntity.getTitle(),
                ingestionEntity.getDescription());
    }

    /**
     * Private method that converts a given ingestion entity to a Post entity.
     * @param ingestionEntity the entity to be converted
     * @param score the associated score for this specific post
     * @return the Post entity
     */
    private PostEntity convertToPostEntity(IngestionEntity ingestionEntity, String score) {
        return new PostEntity(ingestionEntity.getId().hashCode(),
                ingestionEntity.getTitle(),
                ingestionEntity.getThumbnail(),
                ingestionEntity.getDescription(),
                ingestionEntity.getPostDate(),
                score,
                ingestionEntity.getUrl());
    }

}
