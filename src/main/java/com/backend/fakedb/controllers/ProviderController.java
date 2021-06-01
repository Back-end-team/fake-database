package com.backend.fakedb.controllers;

import com.backend.fakedb.entities.ProviderEntity;
import com.backend.fakedb.services.ProviderService;
import com.backend.fakedb.utilities.IntWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path = "provider")
public class ProviderController {

    private final ProviderService providerService;

    @Autowired
    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @GetMapping(path = "/getById")
    public Optional<ProviderEntity> getById(
            @RequestHeader(name = "X-Auth-User") Integer auth_id,
            @RequestHeader(name = "X-Auth-Token") String token,
            @RequestParam(name = "provider_id", required = true) int prov_id) {
        return providerService.getById(auth_id, token, prov_id);
    }


    /**
     * Public method for requesting all providers from the database
     * @param auth_id user's authentication id
     * @param token user's authentication token
     * @return a List with every ProviderEntity from the database
     */
    @GetMapping(path = "/getAll")
    public List<ProviderEntity> getProviders(
            @RequestHeader(name = "X-Auth-User") Integer auth_id,
            @RequestHeader(name = "X-Auth-Token") String token) {
        return providerService.getAll(auth_id, token);
    }

    /**
     * Public method for requesting a specific number of providers from the database
     * @param auth_id user's authentication id
     * @param token user's authentication token
     * @param s used for skipping a number of rows from the database
     * @param c how many rows are necessary
     * @return a List with the ProviderEntity requested
     */
    @GetMapping(path = "/getInterval")
    public List<ProviderEntity> getInterval(
            @RequestHeader(name = "X-Auth-User") Integer auth_id,
            @RequestHeader(name = "X-Auth-Token") String token,
            @RequestParam(name = "skip", required = true) int s,
            @RequestParam(name = "count", required = true) int c) {
        return providerService.getInterval(auth_id, token, s, c);
    }

    /**
     * Public method for requesting the total number of providers enlisted in the database
     * @param auth_id user's authentication id
     * @param token user's authentication token
     * @return the number of providers as a IntWrapper class
     */
    @GetMapping(path = "/getCount")
    public IntWrapper getCount(
            @RequestHeader(name = "X-Auth-User") Integer auth_id,
            @RequestHeader(name = "X-Auth-Token") String token) {
        return providerService.getCount(auth_id, token);
    }

    /**
     * Public method for requesting the number of providers containing the provided string
     * @param auth_id user's authentification id
     * @param token user's authentification token
     * @param query used for providing the string to be searched within the names of the providers
     * @return an IntWrapper containing the number of providers
     */
    @GetMapping(path = "/searchCount")
    public IntWrapper searchCount(
            @RequestHeader(name = "X-Auth-User") Integer auth_id,
            @RequestHeader(name = "X-Auth-Token") String token,
            @RequestParam(name = "query", required = true) String query) {
        return providerService.searchCount(auth_id, token, query);
    }

    /**
     * Public method for requesting a list of providers containing the provided string
     * @param auth_id user's authentification id
     * @param token user's authentification token
     * @param query used for providing the string to be searched within the names of the providers
     * @param s used for skipping a number of rows from the database
     * @param c how many rows are necessary
     * @return a List with the ProviderEntity requested
     */
    @GetMapping(path = "/search")
    public List<ProviderEntity> search(
            @RequestHeader(name = "X-Auth-User") Integer auth_id,
            @RequestHeader(name = "X-Auth-Token") String token,
            @RequestParam(name = "query", required = true) String query,
            @RequestParam(name = "skip", required = true) int s,
            @RequestParam(name = "count", required = true) int c) {
        return providerService.search(auth_id, token, query, s, c);
    }
}
